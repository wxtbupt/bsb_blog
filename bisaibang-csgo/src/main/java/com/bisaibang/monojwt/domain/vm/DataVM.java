package com.bisaibang.monojwt.domain.vm;

/**
 * Created by xiazhen on 2017/6/15.
 */
public class DataVM {
    private Long userNumber;
    private Long nickNameNumber;
    private Long rNumber;

    public Long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }

    public Long getNickNameNumber() {
        return nickNameNumber;
    }

    public void setNickNameNumber(Long nickNameNumber) {
        this.nickNameNumber = nickNameNumber;
    }

    public Long getrNumber() {
        return rNumber;
    }

    public void setrNumber(Long rNumber) {
        this.rNumber = rNumber;
    }

    @Override
    public String toString() {
        return "DataVM{" +
                "userNumber=" + userNumber +
                ", nickNameNumber=" + nickNameNumber +
                ", rNumber=" + rNumber +
                '}';
    }

    public static DataVM create(Integer userNumber, Long nickNameNumber, Integer rNumber){
        DataVM dataVM = new DataVM();
        dataVM.setNickNameNumber(nickNameNumber);
        dataVM.setrNumber(Integer.toUnsignedLong(rNumber));
        dataVM.setUserNumber(Integer.toUnsignedLong(userNumber));
        return dataVM;
    }

}
