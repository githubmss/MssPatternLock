package com.mss.msspatternlock.activities;

import android.content.Intent;
import android.text.TextUtils;

import com.mss.msspatternlock.utils.AppPreferences;
import com.mss.msspatternlock.utils.Constants;

import java.util.List;

import me.zhanghai.android.patternlock.*;


public class ConfirmPatternActivity extends me.zhanghai.android.patternlock.ConfirmPatternActivity {

    AppPreferences mSession;

    @Override
    protected boolean isStealthModeEnabled() {
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String patternSha1 = null;
        mSession = new AppPreferences(this);
        //checks for the value of pattern matches with stored pattern
        if (TextUtils.equals(PatternUtils.patternToSha1String(pattern), mSession.getPrefrenceString(Constants.PATTERN_SHA_VALUE))) {
            Intent main = new Intent(ConfirmPatternActivity.this, MainActivity.class);
            startActivity(main);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onForgotPassword() {
        //In case of forgets Pattern
        startActivity(new Intent(this, SetPatternActivity.class));
        super.onForgotPassword();
    }
}
