package com.autofactory.smilebuy.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


import java.util.Date;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class SmsReceiver extends BroadcastReceiver {
    public final static String RECEIVE_SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public final static String RECEIVE_SECRET_CODE = "RECEIVE_SECRET_CODE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(RECEIVE_SMS_ACTION.equalsIgnoreCase(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[])bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for(int i = 0; i < messages.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            Date curDate = new Date(smsMessage[0].getTimestampMillis());
            Log.d("[문자 수신 시간] " + curDate.toString());

            String origNumber = smsMessage[0].getOriginatingAddress();

            String message = smsMessage[0].getMessageBody().toString();
            Log.d("[문자 내용] 발신자: " + origNumber + ", 내용: " + message);

            if(Constant.SMS_RECEIVE_SENDER.contains(origNumber)) {
                String secretCode = message.replaceAll("\\D+", ""); //str.replaceAll("[^\\.0123456789]","");
                Log.d("[인증번호] " + secretCode);

                Intent i = new Intent(RECEIVE_SECRET_CODE);
                i.putExtra(RECEIVE_SECRET_CODE, secretCode);
                context.sendBroadcast(i);
            }
        }
    }
}
