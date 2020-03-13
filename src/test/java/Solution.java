class Solution {
    public boolean isMonotonic(int[] A) {
        int index=0;
        boolean ascflag=false;
        boolean descflag=false;
        while(index<A.length){
            /**
             * 第一次的时候,或者一直是相等的时候
             */
            if( (ascflag==false && descflag==false) || (ascflag==true && descflag==true) ) {
                if (A[index]<A[index+1]) { //  判断是升序列
                    ascflag=true;
                    descflag=false;
                }

                if (A[index]>A[index+1]) { //  判断是升序列
                    ascflag=false;
                    descflag=true;
                }

                if (A[index]>A[index+1]) { //  不能判断是升序还是降序
                    ascflag=true;
                    descflag=true;
                }
            }

            if(ascflag==true && descflag==false ) { // 升序,判断
                if (A[index]>A[index+1]) {
                    return false;
                }
            }

            if(ascflag==false && descflag==true ) { // 降序,判断
                if (A[index]<A[index+1]) {
                    return false;
                }
            }

        }
        return true;
    }
}