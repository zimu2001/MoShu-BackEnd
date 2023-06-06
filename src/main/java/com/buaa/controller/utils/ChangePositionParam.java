package com.buaa.controller.utils;

public class ChangePositionParam {
    TwoUserParam twoUserParam;
    int targetPosition;

    public TwoUserParam getTwoUserParam() {
        return twoUserParam;
    }

    public void setTwoUserParam(TwoUserParam twoUserParam) {
        this.twoUserParam = twoUserParam;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
    }
}
