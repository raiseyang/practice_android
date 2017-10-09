package com.raise.practice.dagger2;

/**
 * Created by raise.yang on 17/09/28.
 */

public class DaggerContract {

    interface View {

        void showLoading();

    }

    interface Presenter {

        void loadData();
    }

}
