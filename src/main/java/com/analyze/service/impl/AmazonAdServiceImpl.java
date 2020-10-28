package com.analyze.service.impl;

import com.analyze.dao.AmazonAdConsumeSettingDOMapper;
import com.analyze.dao.UserMapper;
import com.analyze.dto.AmazonAdConsumeItemDTO;
import com.analyze.dto.AmazonAdConsumeSettingDTO;
import com.analyze.dto.request.AmazonAdConsumeItemRequest;
import com.analyze.dto.request.AmazonAdConsumeSettingRequest;
import com.analyze.model.AmazonAdConsumeItemDO;
import com.analyze.model.AmazonAdConsumeSettingDO;
import com.analyze.model.User;
import com.analyze.service.AmazonAdService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The service deal with the amazon ad consume setting
 *
 */
@Service
public class AmazonAdServiceImpl implements AmazonAdService {

    /**
     * The ad setting in the service
     *
     */
    private List<AmazonAdConsumeSettingDTO> activeAdConsumeSetting;

    /**
     * The active ad setting last update time
     */
    private LocalDateTime updatedAt;

    private PlatformTransactionManager transactionManager;

    private AmazonAdConsumeSettingDOMapper amazonAdConsumeSettingDOMapper;

    private UserMapper userMapper;

    private ModelMapper modelMapper;

    @Autowired
    public AmazonAdServiceImpl(PlatformTransactionManager transactionManager, AmazonAdConsumeSettingDOMapper amazonAdConsumeSettingDOMapper, UserMapper userMapper, ModelMapper modelMapper) {
        this.transactionManager = transactionManager;
        this.amazonAdConsumeSettingDOMapper = amazonAdConsumeSettingDOMapper;
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AmazonAdConsumeSettingDTO> getAllSetting() {
        return amazonAdConsumeSettingDOMapper.getAllSetting().stream().map(this::settingDOToDto).collect(Collectors.toList());
    }

    @Override
    public List<AmazonAdConsumeSettingDTO> getAllActiveSetting() {
        List<AmazonAdConsumeSettingDTO> activeAdConsumeSetting = amazonAdConsumeSettingDOMapper.getAllActiveSetting().stream().map(this::settingDOToDto).collect(Collectors.toList());

        this.activeAdConsumeSetting = activeAdConsumeSetting;
        this.updatedAt = LocalDateTime.now();

        return activeAdConsumeSetting;
    }

    @Override
    public AmazonAdConsumeSettingDTO getSettingById(Long settingId) {

        if(settingId == null){
            throw new IllegalArgumentException("[getSettingById] Setting id cannot be null");
        }

        return settingDOToDto(amazonAdConsumeSettingDOMapper.getSettingById(settingId));
    }

    @Override
    public List<AmazonAdConsumeSettingDTO> getAllSettingByCreatedUser(Long userId) {
        return amazonAdConsumeSettingDOMapper.getAllSettingByCreatedUser(userId).stream().map(this::settingDOToDto).collect(Collectors.toList());
    }

    @Override
    public List<AmazonAdConsumeSettingDTO> getAllActiveSettingByCreatedUser(Long userId) {
        return amazonAdConsumeSettingDOMapper.getAllSettingByCreatedUser(userId).stream().map(this::settingDOToDto).collect(Collectors.toList());
    }

    @Override
    public AmazonAdConsumeSettingDTO insertSetting(AmazonAdConsumeSettingRequest newSettingRequest) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        Long userId = Long.valueOf(newSettingRequest.getUserId());

        Long newSettingId = transactionTemplate.execute(transactionStatus -> {
            User userDO = userMapper.getUserAccountByUserId(userId);

            if(userDO == null){
                throw new IllegalArgumentException(String.format("[insertSetting] user id %s not exist", userId));
            }

            AmazonAdConsumeSettingDTO newSetting = modelMapper.map(newSettingRequest, AmazonAdConsumeSettingDTO.class);
            newSetting.setCreatedBy(userId.toString());
            newSetting.setUpdatedBy(userId.toString());

            AmazonAdConsumeSettingDO newSettingDO = settingDtoToDO(newSetting);

            newSettingDO.setCreatedBy(userDO.getUserid().toString());
            newSettingDO.setUpdatedBy(userDO.getUserid().toString());

            amazonAdConsumeSettingDOMapper.insertSetting(newSettingDO);

            return newSettingDO.getId();
        });

        return getSettingById(newSettingId);

    }

    @Override
    public AmazonAdConsumeSettingDTO updateSetting(Long settingId, AmazonAdConsumeSettingRequest updateSetting) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        Long userId = Long.valueOf(updateSetting.getUserId());

        Long updateId = transactionTemplate.execute(transactionStatus -> {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[insertSetting] user id %s not exist", userId));
                }

                AmazonAdConsumeSettingDO oldSetting = amazonAdConsumeSettingDOMapper.getSettingById(settingId);

                if (oldSetting == null){
                    throw new IllegalArgumentException(String.format("[insertSetting] setting id %s not exist", settingId));
                }

                oldSetting.setName(updateSetting.getName());
                oldSetting.setDescription(updateSetting.getDescription());
                oldSetting.setSearchWords(updateSetting.getSearchWords());

                oldSetting.setUpdatedBy(userId.toString());

                amazonAdConsumeSettingDOMapper.updateSetting(oldSetting);

                return oldSetting.getId();
        });

        return getSettingById(updateId);
    }

    @Override
    public void startAdConsume(Long userId, Long settingId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[startAdConsume] user id %s not exist", userId));
                }

                AmazonAdConsumeSettingDO oldSetting = amazonAdConsumeSettingDOMapper.getSettingById(settingId);

                if (oldSetting == null){
                    throw new IllegalArgumentException(String.format("[startAdConsume] setting id %s not exist", settingId));
                }

                amazonAdConsumeSettingDOMapper.startAdConsume(userId, settingId);
            }
        });
    }

    @Override
    public void stopAdConsume(Long userId, Long settingId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[stopAdConsume] user id %s not exist", userId));
                }

                AmazonAdConsumeSettingDO oldSetting = amazonAdConsumeSettingDOMapper.getSettingById(settingId);

                if (oldSetting == null){
                    throw new IllegalArgumentException(String.format("[stopAdConsume] setting id %s not exist", settingId));
                }

                amazonAdConsumeSettingDOMapper.stopAdConsume(userId, settingId);
            }
        });
    }

    @Override
    public void insertAdConsumeItem(Long settingId, AmazonAdConsumeItemRequest newItemRequest) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        Long userId = Long.valueOf(newItemRequest.getUserId());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[insertSetting] user id %s not exist", userId));
                }

                AmazonAdConsumeItemDO newSettingDO = modelMapper.map(newItemRequest, AmazonAdConsumeItemDO.class);
                newSettingDO.setSettingId(settingId);

                newSettingDO.setCreatedBy(userId.toString());
                newSettingDO.setUpdatedBy(userId.toString());

                amazonAdConsumeSettingDOMapper.insertAdConsumeItem(newSettingDO);
            }
        });
    }

    @Override
    public void updateAdConsumeItem(Long settingId, Long itemId, AmazonAdConsumeItemRequest updateRequest) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        Long userId = Long.valueOf(updateRequest.getUserId());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[updateAdConsumeItem] user id %s not exist", userId));
                }

                AmazonAdConsumeItemDO oldItem = amazonAdConsumeSettingDOMapper.getSettingItemById(itemId);

                if(oldItem == null){
                    throw new IllegalArgumentException(String.format("[updateAdConsumeItem] item id %s not exist", itemId));
                }

                if (!oldItem.getSettingId().equals(settingId)){
                    throw new IllegalArgumentException(String.format("Amazon ad Item %s is not in the setting %", itemId, settingId));
                }

                oldItem.setName(updateRequest.getName() == null ? "" : updateRequest.getName().trim());
                oldItem.setAsin(updateRequest.getAsin() == null ? "" :updateRequest.getAsin().trim());
                oldItem.setKeyword(updateRequest.getKeyword() == null ? "" :updateRequest.getKeyword().trim());
                oldItem.setUpdatedBy(userId.toString());

                amazonAdConsumeSettingDOMapper.updateAdConsumeItem(oldItem);
            }
        });

    }

    @Override
    public void whileListItem(Long userId, Long itemId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[whileListItem] user id %s not exist", userId));
                }

                AmazonAdConsumeItemDO oldItem = amazonAdConsumeSettingDOMapper.getSettingItemById(itemId);

                if(oldItem == null){
                    throw new IllegalArgumentException(String.format("[whileListItem] item id %s not exist", itemId));
                }

                oldItem.setConsume(false);
                oldItem.setUpdatedBy(userId.toString());

                amazonAdConsumeSettingDOMapper.whileListItem(userId, itemId);
            }
        });
    }

    @Override
    public void blackListItem(Long userId, Long itemId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[blackListItem] user id %s not exist", userId));
                }

                AmazonAdConsumeItemDO oldItem = amazonAdConsumeSettingDOMapper.getSettingItemById(itemId);

                if(oldItem == null){
                    throw new IllegalArgumentException(String.format("[blackListItem] item id %s not exist", itemId));
                }

                amazonAdConsumeSettingDOMapper.blackListItem(userId, itemId);
            }
        });
    }

    @Override
    public void removeSetting(Long userId, Long settingId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[removeSetting] user id %s not exist", userId));
                }

                AmazonAdConsumeSettingDO oldSetting = amazonAdConsumeSettingDOMapper.getSettingById(settingId);

                if (oldSetting == null){
                    throw new IllegalArgumentException(String.format("[removeSetting] setting id %s not exist", settingId));
                }

                oldSetting.setRemoved(true);
                oldSetting.setUpdatedBy(userId.toString());

                amazonAdConsumeSettingDOMapper.removeSetting(userId, settingId);
            }
        });
    }

    @Override
    public void removeItem(Long userId, Long itemId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                User userDO = userMapper.getUserAccountByUserId(userId);

                if(userDO == null){
                    throw new IllegalArgumentException(String.format("[removeItem] user id %s not exist", userId));
                }

                AmazonAdConsumeItemDO oldItem = amazonAdConsumeSettingDOMapper.getSettingItemById(itemId);

                if(oldItem == null){
                    throw new IllegalArgumentException(String.format("[removeItem] item id %s not exist", itemId));
                }

                amazonAdConsumeSettingDOMapper.removeItem(userId, itemId);
            }
        });
    }

    private AmazonAdConsumeSettingDTO settingDOToDto(AmazonAdConsumeSettingDO amazonAdConsumeSettingDO){

        if (amazonAdConsumeSettingDO == null){
            return null;
        }

        return modelMapper.map(amazonAdConsumeSettingDO, AmazonAdConsumeSettingDTO.class);
    }

    private AmazonAdConsumeSettingDO settingDtoToDO(AmazonAdConsumeSettingDTO amazonAdConsumeSettingDTO){
        return modelMapper.map(amazonAdConsumeSettingDTO, AmazonAdConsumeSettingDO.class);
    }

    private AmazonAdConsumeItemDTO itemDOToDto(AmazonAdConsumeItemDO amazonAdConsumeItemDO){
        return modelMapper.map(amazonAdConsumeItemDO, AmazonAdConsumeItemDTO.class);
    }

    private AmazonAdConsumeItemDO itemDtoToDO(AmazonAdConsumeItemDTO amazonAdConsumeItemDTO){
        return modelMapper.map(amazonAdConsumeItemDTO, AmazonAdConsumeItemDO.class);
    }
}
