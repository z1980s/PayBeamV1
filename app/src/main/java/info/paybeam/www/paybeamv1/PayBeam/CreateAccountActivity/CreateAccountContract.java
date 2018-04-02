package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.view.View;

/**
 * Created by zicokuang on 2/4/18.
 */

public interface CreateAccountContract
{
    interface CreateAccountView
    {
        void onSuccessView();
        void onFailureView();
    }

    interface CreateAccountPresenter
    {
        void onSubmitButtonClick(View view);
    }
}
