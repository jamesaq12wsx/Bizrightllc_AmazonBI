package com.analyze.service;

import com.analyze.dto.AmazonAdConsumeItemDTO;
import com.analyze.dto.AmazonAdConsumeSettingDTO;
import com.analyze.dto.request.AmazonAdConsumeItemRequest;
import com.analyze.dto.request.AmazonAdConsumeSettingRequest;

import java.util.List;

public interface AmazonAdService {

    /**
     * Get all setting
     *
     * @return
     */
    List<AmazonAdConsumeSettingDTO> getAllSetting();

    /**
     * Get all active setting
     *
     * @return
     */
    List<AmazonAdConsumeSettingDTO> getAllActiveSetting();

    /**
     * Get Setting by id
     *
     * @param settingId
     * @return
     */
    AmazonAdConsumeSettingDTO getSettingById(Long settingId);

    /**
     * Get all setting by created user id
     *
     * @param userId
     * @return
     */
    List<AmazonAdConsumeSettingDTO> getAllSettingByCreatedUser(Long userId);

    /**
     * Get all active setting by created user id
     *
     * @param userId
     * @return
     */
    List<AmazonAdConsumeSettingDTO> getAllActiveSettingByCreatedUser(Long userId);

    /**
     * Create new setting
     *
     * @param newSetting
     */
    AmazonAdConsumeSettingDTO insertSetting(AmazonAdConsumeSettingRequest newSetting);

    /**
     * Update setting
     *
     * @param updateSetting
     */
    AmazonAdConsumeSettingDTO updateSetting(Long settingId, AmazonAdConsumeSettingRequest updateSetting);

    /**
     * Active ad consume
     *
     * @param settingId
     */
    void startAdConsume(Long userId, Long settingId);

    /**
     * Disactive the ad consume
     *
     * @param settingId
     */
    void stopAdConsume(Long userId, Long settingId);

    /**
     * Create new item in setting
     *
     * @param newItem
     */
    void insertAdConsumeItem(Long settingId, AmazonAdConsumeItemRequest newItem);

    /**
     * update item setting
     *
     * @param item
     */
    void updateAdConsumeItem(Long settingId, Long itemId, AmazonAdConsumeItemRequest item);

    /**
     * Set item in while list
     *
     * @param itemId
     */
    void whileListItem(Long userId, Long itemId);

    /**
     * Set item in black list
     *
     * @param itemId
     */
    void blackListItem(Long userId, Long itemId);

    /**
     * Remove setting
     *
     * @param settingId
     */
    void removeSetting(Long userId, Long settingId);

    /**
     * Remove item
     *
     * @param itemId
     */
    void removeItem(Long userId, Long itemId);

}
