package blackcap.nationalescape.Uitility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by 박효근 on 2018-03-12.
 */

public class SMSReceiver extends BroadcastReceiver {
    String str = "";
    // 지정한 특정 액션이 일어나면 수행되는 메서드
    public String onResult(){
        return str;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        // SMS를 받았을 경우에만 반응하도록 if문을 삽입
        if (intent.getAction().equals(
                "android.provider.Telephony.SMS_RECEIVED")) {
            StringBuilder sms = new StringBuilder();    // SMS문자를 저장할 곳
            Bundle bundle = intent.getExtras();         // Bundle객체에 문자를 받아온다

            if (bundle != null) {
                // 번들에 포함된 문자 데이터를 객체 배열로 받아온다
                Object[] pdus = (Object[]) bundle.get("pdus");

                SmsMessage [] msgs
                        = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
// PDU 포맷으로 되어 있는 메시지를 복원합니다.
                    msgs[i] = SmsMessage
                            .createFromPdu((byte[]) pdus[i]);
                    str = msgs[i].getMessageBody().toString();
                    Log.i("test", str);
                }
            }
        }
    }

}
