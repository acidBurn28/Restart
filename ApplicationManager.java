/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.wifi.WifiManager
 *  android.net.wifi.WifiManager$WifiLock
 *  android.os.Environment
 *  android.os.Handler
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.OutOfMemoryError
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Timer
 *  java.util.TimerTask
 */
package com.fsoft.vktest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import com.fsoft.vktest.AccountManager;
import com.fsoft.vktest.IhaSmartProcessor;
import com.fsoft.vktest.MessageComparer;
import com.fsoft.vktest.VkCommunicator;
import com.fsoft.vktest.common.Command;
import com.fsoft.vktest.common.CommandParser;
import com.fsoft.vktest.windows.ConsoleView;
import com.fsoft.vktest.windows.TabsActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ApplicationManager {
    public static String botName;
    public static String botcmd;
    public static String programName;
    public TabsActivity activity = null;
    private Timer autoSaveTimer = null;
    private ArrayList<Command> commands;
    public Handler handler;
    public MessageComparer messageComparer;
    public IhaSmartProcessor messageProcessor;
    public boolean running = true;
    public AccountManager vkAccounts;
    public VkCommunicator vkCommunicator;
    private WifiManager.WifiLock wifiLock = null;

    static {
        programName = "DrAcid_Burn_VK_Lirik_bot";
        botName = "bot";
        botcmd = "botcmd";
    }

    public ApplicationManager(TabsActivity tabsActivity, String string) {
        this.activity = tabsActivity;
        botName = string;
        this.handler = new Handler();
        this.vkAccounts = new AccountManager(this);
        this.vkCommunicator = new VkCommunicator(this);
        this.messageProcessor = new IhaSmartProcessor(this, string);
        this.messageComparer = new MessageComparer(this, string);
        this.commands = new ArrayList();
        this.commands.add((Object)new SetBotName());
        this.commands.add((Object)new Save());
        this.commands.add((Object)new Status());
        this.commands.add((Object)this.vkAccounts);
        this.commands.add((Object)this.vkCommunicator);
        this.commands.add((Object)tabsActivity);
        this.commands.add((Object)this.messageProcessor);
        this.commands.add((Object)this.messageComparer);
    }

    public static String arrayToString(String[] arrstring) {
        String string = " ";
        for (int i = 0; i < arrstring.length; ++i) {
            string = string + arrstring[i];
            if (i >= -1 + arrstring.length) continue;
            string = string + " ";
        }
        return string;
    }

    public static String botMark() {
        if (botName.equals((Object)"") || botName.equals((Object)"EMPTY")) {
            return "";
        }
        return "(" + botName + ") ";
    }

    public static String getHomeFolder() {
        return (Object)Environment.getExternalStorageDirectory() + File.separator + programName;
    }

    private boolean isPackageInstalled(Context context, String string) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(string, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException var4_4) {
            return false;
        }
    }

    public static String log(String string) {
        if (TabsActivity.consoleView != null) {
            TabsActivity.consoleView.log("# " + string);
        }
        return string;
    }

    private void startAutoSaving() {
        ApplicationManager.log(". \u0417\u0430\u043f\u0443\u0441\u043a \u0430\u0432\u0442\u043e\u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f...");
        if (this.autoSaveTimer == null) {
            this.autoSaveTimer = new Timer();
            this.autoSaveTimer.schedule((TimerTask)new TimerTask(){

                public void run() {
                    ApplicationManager.log(". \u0410\u0432\u0442\u043e\u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u0435...");
                    ApplicationManager.this.processCommand("save");
                }
            }, 1800000, 1800000);
        }
    }

    private void startWiFiLock() {
        ApplicationManager.log(". \u0411\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u043a\u0430 \u0441\u043e\u0441\u0442\u043e\u044f\u043d\u0438\u044f Wi-Fi...");
        try {
            this.wifiLock = ((WifiManager)this.activity.getSystemService("wifi")).createWifiLock(programName);
            this.wifiLock.acquire();
            return;
        }
        catch (Exception var2_1) {
            var2_1.printStackTrace();
            ApplicationManager.log("\u041e\u0448\u0438\u0431\u043a\u0430 \u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u043a\u0438 Wi-Fi: " + var2_1.toString());
            return;
        }
    }

    private void stopAutoSaving() {
        ApplicationManager.log(". \u041e\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0430\u0432\u0442\u043e\u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f...");
        if (this.autoSaveTimer != null) {
            this.autoSaveTimer.cancel();
            this.autoSaveTimer = null;
        }
    }

    private void stopWiFiLock() {
        try {
            if (this.wifiLock != null) {
                ApplicationManager.log(". \u0420\u0430\u0437\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u043a\u0430 Wi-Fi...");
                this.wifiLock.release();
            }
            return;
        }
        catch (Exception var1_1) {
            var1_1.printStackTrace();
            ApplicationManager.log("\u041e\u0448\u0438\u0431\u043a\u0430 \u0440\u0430\u0437\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u043a\u0438 Wi-Fi: " + var1_1.toString());
            return;
        }
    }

    public void close() {
        try {
            this.running = false;
            this.stopAutoSaving();
            this.stopWiFiLock();
            this.processCommand("save");
            this.vkAccounts.close();
            this.vkCommunicator.close();
            this.messageProcessor.close();
            this.messageComparer.close();
            return;
        }
        catch (Exception var1_1) {
            var1_1.printStackTrace();
            ApplicationManager.log("! \u041e\u0448\u0438\u0431\u043a\u0430 \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0438\u044f: " + var1_1.toString());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public String getCommandsHelp() {
        String string;
        try {
            string = this.activity.getResources().getString(2131034113) + ", \u0440\u0430\u0437\u0440\u0430\u0431\u043e\u0442\u0430\u043d\u043d\u044b\u0439 Dr. Failov.\n" + "\u041a\u043e\u043c\u0430\u043d\u0434\u044b \u043c\u043e\u0436\u043d\u043e \u043f\u0438\u0441\u0430\u0442\u044c \u0432\u0435\u0437\u0434\u0435, \u0433\u0434\u0435 \u0431\u043e\u0442 \u043c\u043e\u0436\u0435\u0442 \u043e\u0442\u0432\u0435\u0447\u0430\u0442\u044c, \u043d\u0430\u043f\u0440\u0438\u043c\u0435\u0440, \u043d\u0430 \u0441\u0442\u0435\u043d\u0435, \u0432 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430\u0445 \"\u041d\u0430\u043f\u0438\u0441\u0430\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0431\u043e\u0442\u0443\" , \u0438\u043b\u0438 \u0432 \u041b\u0421, \u0435\u0441\u043b\u0438 \u0438\u0445 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0430 \u0432\u043a\u043b\u044e\u0447\u0435\u043d\u0430.\n\n" + "[ \u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u0432\u0441\u0435 \u0432\u043d\u0435\u0441\u0435\u043d\u043d\u044b\u0435 \u0432 \u0431\u0430\u0437\u044b \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u044f ]\n" + "---| botcmd save\n\n" + "[ \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u043f\u043e\u0434\u0440\u043e\u0431\u043d\u044b\u0439 \u043e\u0442\u0447\u0451\u0442 \u043e \u0441\u043e\u0441\u0442\u043e\u044f\u043d\u0438\u0438 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u044b ]\n" + "---| botcmd status\n\n";
        }
        catch (Exception var1_4) {
            var1_4.printStackTrace();
            return "! \u0413\u043b\u043e\u0431\u0430\u043b\u044c\u043d\u0430\u044f \u043e\u0448\u0438\u0431\u043a\u0430 \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u044f \u0441\u043f\u0440\u0430\u0432\u043a\u0438: " + var1_4.toString();
        }
        for (int i = 0; i < this.commands.size(); ++i) {
            String string2;
            string = string2 = string + ((Command)this.commands.get(i)).getHelp();
        }
        return string;
    }

    public Long getUserID() {
        return this.vkCommunicator.getOwnerId();
    }

    public Long getUserID(String string) {
        try {
            Long l = Long.parseLong((String)string);
            return l;
        }
        catch (Exception var2_3) {
            if (string != null) {
                string = string.replace((CharSequence)"https://m.vk.com/", (CharSequence)"").replace((CharSequence)"http://m.vk.com/", (CharSequence)"").replace((CharSequence)"https://vk.com/", (CharSequence)"").replace((CharSequence)"http://vk.com/", (CharSequence)"").replace((CharSequence)"vk.com/", (CharSequence)"");
            }
            return this.vkCommunicator.getOwnerId(string);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
   // public boolean isDonated() {
       // boolean bl = this.isPackageInstalled((Context)this.activity, "com.fsoft.vktest");
       // boolean bl2 = false;
    //    if (!bl) return bl2;
    //    Iterator iterator = this.activity.getPackageManager().queryIntentActivities(new Intent("CHECK_VK_IHA_BOT"), 0).iterator();
    //do {
    //        boolean bl3 = iterator.hasNext();
      //      bl2 = false;
      //      if (!bl3) return bl2;
   //     } while (!((android.content.pm.ResolveInfo)iterator.next()).activityInfo.name.equals((Object)"com.fsoft.vktest"));
  //      return true;
    }

    public boolean isStandby() {
        return this.vkCommunicator.standby;
    }

    public void load() {
        new Thread((Runnable)new Runnable(){

            public void run() {
                try {
                    ApplicationManager.log(". \u0417\u0430\u0443\u0433\u0440\u0443\u0437\u043a\u0430 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u044b " + ApplicationManager.botName + " ...");
                    ApplicationManager.this.startAutoSaving();
                    if (ApplicationManager.this.isDonated()) {
                        ApplicationManager.this.startWiFiLock();
                    }
                    ApplicationManager.this.messageComparer.load();
                    ApplicationManager.this.messageProcessor.load();
                    ApplicationManager.this.vkAccounts.load();
                    ApplicationManager.this.vkCommunicator.load();
                    ApplicationManager.botName = ApplicationManager.this.activity.getPreferences(0).getString("botName", ApplicationManager.botName);
                    if (!ApplicationManager.this.isDonated() && (ApplicationManager.botName.equals((Object)"") || ApplicationManager.botName.equals((Object)"EMPTY"))) {
                        ApplicationManager.botName = "bot";
                    }
                    ApplicationManager.log(". \u0418\u043c\u044f \u0431\u043e\u0442\u0430 " + ApplicationManager.botName + " \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u043e.\n");
                    ApplicationManager.log(". \u041f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0430 " + ApplicationManager.botName + " \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u0430.");
                    return;
                }
                catch (Exception var1_1) {
                    var1_1.printStackTrace();
                    ApplicationManager.log("! \u041e\u0448\u0438\u0431\u043a\u0430 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0438: " + var1_1.toString());
                    return;
                }
            }
        }).start();
    }

    public void messageBox(String string) {
        if (this.activity != null) {
            this.activity.messageBox(string);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    public String processCommand(String var1_1) {
        var2_2 = "";
        try {
            for (var3_3 = 0; var3_3 < this.commands.size(); ++var3_3) {
                var2_2 = var2_2 + ((Command)this.commands.get(var3_3)).process(var1_1);
            }
            if (var2_2.equals((Object)"") == false) return var2_2;
            return "\u041e\u0448\u0438\u0431\u043a\u0430 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0438 \u043a\u043e\u043c\u0430\u043d\u0434\u044b: \u0442\u0430\u043a\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u043d\u0435\u0442.\n";
        }
        catch (Error var4_4) {}
        ** GOTO lbl-1000
        catch (Exception var4_6) {}
lbl-1000: // 2 sources:
        {
            var4_5.printStackTrace();
            return "\u0413\u043b\u043e\u0431\u0430\u043b\u044c\u043d\u0430\u044f \u043e\u0448\u0438\u0431\u043a\u0430 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0438 \u043a\u043e\u043c\u0430\u043d\u0434\u044b: " + var4_5.toString();
        }
    }

    public String processMessage(String string, Long l) {
        try {
            String string2 = this.messageProcessor.processMessage(string, l);
            return string2;
        }
        catch (Exception var4_4) {
            var4_4.printStackTrace();
            return "\u0413\u043b\u043e\u0431\u0430\u043b\u044c\u043d\u0430\u044f \u043e\u0448\u0438\u0431\u043a\u0430 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f: " + var4_4.toString();
        }
        catch (OutOfMemoryError var3_5) {
            var3_5.printStackTrace();
            return "\u0413\u043b\u043e\u0431\u0430\u043b\u044c\u043d\u0430\u044f \u043d\u0435\u0445\u0432\u0430\u0442\u043a\u0430 \u043f\u0430\u043c\u044f\u0442\u0438: " + var3_5.toString();
        }
    }

    public void sleep(int n) {
        long l = n;
        try {
            Thread.sleep((long)l);
            return;
        }
        catch (Exception var4_3) {
            var4_3.printStackTrace();
            return;
        }
    }

    public String[] splitText(String string, int n) {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            if (i != 0 && i % n == 0) {
                arrayList.add((Object)stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(string.charAt(i));
        }
        if (stringBuilder.length() > 0) {
            arrayList.add((Object)stringBuilder.toString());
        }
        String[] arrstring = new String[arrayList.size()];
        for (int j = 0; j < arrayList.size(); ++j) {
            arrstring[j] = (String)arrayList.get(j);
        }
        return arrstring;
    }

    public String[] trimArray(String[] arrstring) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < arrstring.length; ++i) {
            if (arrstring[i] == null || arrstring[i].equals((Object)"")) continue;
            arrayList.add((Object)arrstring[i]);
        }
        String[] arrstring2 = new String[arrayList.size()];
        for (int j = 0; j < arrayList.size(); ++j) {
            arrstring2[j] = (String)arrayList.get(j);
        }
        return arrstring2;
    }

    class Save
    implements Command {
        Save() {
        }

        @Override
        public String getHelp() {
            return "";
        }

        @Override
        public String process(String string) {
            if (new CommandParser(string).getWord().equals((Object)"save")) {
                SharedPreferences.Editor editor = ApplicationManager.this.activity.getPreferences(0).edit();
                editor.putString("botName", ApplicationManager.botName);
                editor.commit();
                return ApplicationManager.log(". \u0418\u043c\u044f \u0431\u043e\u0442\u0430 " + ApplicationManager.botName + " \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u043e.\n");
            }
            return "";
        }
    }

    class SetBotName
    implements Command {
        SetBotName() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String getHelp() {
            String string;
            StringBuilder stringBuilder = new StringBuilder().append("[ \u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u043c\u0435\u0442\u043a\u0443 \u0431\u043e\u0442\u0430 (\u043e\u0442\u043e\u0431\u0440\u0430\u0436\u0430\u0435\u0442\u0441\u044f \u0432 \u0441\u043a\u043e\u0431\u043a\u0430\u0445 \u043f\u0435\u0440\u0435\u0434 \u0442\u0435\u043a\u0441\u0442\u043e\u043c \u043e\u0442\u0432\u0435\u0442\u0430) ]\n[ EMPTY \u0431\u0443\u0434\u0435\u0442 \u043e\u0437\u043d\u0430\u0447\u0430\u0442\u044c \u043f\u0443\u0441\u0442\u0443\u044e \u043c\u0435\u0442\u043a\u0443 ");
            if (ApplicationManager.this.isDonated()) {
                string = "";
                do {
                    return stringBuilder.append(string).append(" ]\n").append("---| botcmd setbotname <\u043d\u043e\u0432\u0430\u044f \u043c\u0435\u0442\u043a\u0430> \n\n").toString();
                    break;
                } while (true);
            }
            string = "(\u0434\u043e\u0441\u0442\u0443\u043f\u043d\u043e \u0442\u043e\u043b\u044c\u043a\u043e \u0432 \u043f\u043e\u043b\u043d\u043e\u0439 \u0432\u0435\u0440\u0441\u0438\u0438)";
            return stringBuilder.append(string).append(" ]\n").append("---| botcmd setbotname <\u043d\u043e\u0432\u0430\u044f \u043c\u0435\u0442\u043a\u0430> \n\n").toString();
        }

        @Override
        public String process(String string) {
            CommandParser commandParser = new CommandParser(string);
            if (commandParser.getWord().equals((Object)"setbotname")) {
                String string2 = commandParser.getText();
                if ((string2.equals((Object)"") || string2.equals((Object)"EMPTY")) && !ApplicationManager.this.isDonated()) {
                    return "\u041f\u0443\u0441\u0442\u0430\u044f \u043c\u0435\u0442\u043a\u0430 \u0434\u043e\u0441\u0442\u0443\u043f\u043d\u0430 \u0442\u043e\u043b\u044c\u043a\u043e \u0432 \u043f\u043e\u043b\u043d\u043e\u0439 \u0432\u0435\u0440\u0441\u0438\u0438 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u044b.";
                }
                ApplicationManager.botName = string2;
                return "\u041d\u043e\u0432\u0430\u044f \u043c\u0435\u0442\u043a\u0430 \u0431\u043e\u0442\u0430: \"" + ApplicationManager.botMark() + "\".";
            }
            return "";
        }
    }

    class Status
    implements Command {
        Status() {
        }

        @Override
        public String getHelp() {
            return "";
        }

        @Override
        public String process(String string) {
            if (new CommandParser(string).getWord().equals((Object)"status")) {
                return "\u0418\u043c\u044f \u0431\u043e\u0442\u0430: " + ApplicationManager.botName + "\n";
            }
            return "";
        }
    }

}

