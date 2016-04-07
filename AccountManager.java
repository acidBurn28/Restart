/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Comparator
 *  java.util.Random
 *  java.util.Timer
 *  java.util.TimerTask
 */
package com.fsoft.vktest;

import com.fsoft.vktest.ApplicationManager;
import com.fsoft.vktest.VkAccount;
import com.fsoft.vktest.VkCommunicator;
import com.fsoft.vktest.common.Command;
import com.fsoft.vktest.common.CommandParser;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AccountManager
extends ArrayList<VkAccount>
implements Command {
    private ApplicationManager applicationManager;
    private ArrayList<Command> commands = new ArrayList();
    private final Object getActiveSync = new Object();
    private String homeFolder = "";

    public AccountManager(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
        this.homeFolder = ApplicationManager.getHomeFolder() + File.separator + "accounts";
        this.commands.add((Object)new Status());
        this.commands.add((Object)new AddAccount());
    }

    static /* synthetic */ ApplicationManager access$200(AccountManager accountManager) {
        return accountManager.applicationManager;
    }

    private void log(String string) {
        ApplicationManager.log(string);
    }

    private void sleep(int n) {
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

    public void addAccount() {
        VkAccount vkAccount = new VkAccount(this.applicationManager, this.getNextAccountFileName());
        this.add((Object)vkAccount);
        if (this.applicationManager.vkCommunicator.walls.size() == 0) {
            this.log(this.applicationManager.vkCommunicator.addOwnerID(vkAccount.id));
        }
    }

    public void addAccount(final VkAccount vkAccount) {
        this.add((Object)vkAccount);
        new Timer().schedule((TimerTask)new TimerTask(){

            public void run() {
                if (AccountManager.access$200((AccountManager)AccountManager.this).vkCommunicator.walls.size() == 0) {
                    AccountManager.this.log(AccountManager.access$200((AccountManager)AccountManager.this).vkCommunicator.addOwnerID(vkAccount.id));
                }
            }
        }, 2000);
    }

    public void addAccount(String string, long l) {
        if (this.get(l) == null) {
            VkAccount vkAccount = new VkAccount(this.applicationManager, this.getNextAccountFileName(), string, l);
            this.add((Object)vkAccount);
            if (this.applicationManager.vkCommunicator.walls.size() == 0) {
                this.log(this.applicationManager.vkCommunicator.addOwnerID(vkAccount.id));
            }
            return;
        }
        this.log("! \u041f\u043e\u0432\u0442\u043e\u0440 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u0430 " + l + " \u043d\u0435 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d.");
    }

    public void close() {
        for (int i = 0; i < this.size(); ++i) {
            ((VkAccount)this.get(i)).close();
        }
        this.applicationManager = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public VkAccount get(long l) {
        Object object;
        Object object2 = object = this.getActiveSync;
        synchronized (object2) {
            for (int i = 0; i < this.size(); ++i) {
                VkAccount vkAccount = (VkAccount)this.get(i);
                if (vkAccount.id != l) continue;
                return vkAccount;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public VkAccount getActive() {
        Object object;
        Object object2 = object = this.getActiveSync;
        synchronized (object2) {
            Random random = new Random();
            block2 : do {
                ArrayList arrayList = new ArrayList();
                int n = 0;
                do {
                    if (n < this.size()) {
                        VkAccount vkAccount = (VkAccount)this.get(n);
                        if (vkAccount.isReady()) {
                            arrayList.add((Object)vkAccount);
                        }
                    } else {
                        if (arrayList.size() > 0) {
                            return (VkAccount)arrayList.get(random.nextInt(arrayList.size()));
                        }
                        this.sleep(500);
                        continue block2;
                    }
                    ++n;
                } while (true);
                break;
            } while (true);
        }
    }

    @Override
    public String getHelp() {
        String string = "";
        for (int i = 0; i < this.commands.size(); ++i) {
            string = string + ((Command)this.commands.get(i)).getHelp();
        }
        for (int j = 0; j < this.size(); ++j) {
            string = string + ((VkAccount)this.get(j)).getHelp();
        }
        return string;
    }

    public String getNextAccountFileName() {
        this.log(". \u041f\u043e\u0438\u0441\u043a \u043d\u043e\u0432\u043e\u0433\u043e \u0438\u043c\u0435\u043d\u0438 \u0444\u0430\u0439\u043b\u0430 ...");
        int n = 0;
        do {
            String string;
            if (!new File(string = this.homeFolder + File.separator + "account" + n).exists()) {
                this.log(". \u041d\u0430\u0439\u0434\u0435\u043d\u043e \u0438\u043c\u044f: " + string);
                return string;
            }
            ++n;
        } while (true);
    }

    public void load() {
        File file = new File(this.homeFolder);
        this.log(". \u041f\u043e\u0438\u0441\u043a \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432 \u0432 " + file.getPath() + " ...");
        Object[] arrobject = file.listFiles();
        if (arrobject != null) {
            Arrays.sort((Object[])arrobject, (Comparator)new Comparator<File>(){

                public int compare(File file, File file2) {
                    return file.getPath().compareTo(file2.getPath());
                }
            });
            for (int i = 0; i < arrobject.length; ++i) {
                Object object = arrobject[i];
                if (!object.isFile()) continue;
                this.log(". \u0414\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u0430 " + object.getPath());
                this.addAccount(new VkAccount(this.applicationManager, object.getPath()));
                this.sleep(1000);
            }
        } else {
            this.log("\u0414\u043b\u044f \u0440\u0430\u0431\u043e\u0442\u044b \u0431\u043e\u0442\u0430 \u043d\u0443\u0436\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442.\n\u041f\u0435\u0440\u0435\u0439\u0434\u0438 \u043d\u0430 \u0432\u043a\u043b\u0430\u0434\u043a\u0443 \u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b \u0438 \u043d\u0430\u0436\u043c\u0438 \u043a\u043d\u043e\u043f\u043a\u0443 \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442.\n\u0412\u043e\u0439\u0434\u0438 \u0432 \u0441\u0432\u043e\u0439 \u0438\u043b\u0438 \u0444\u0435\u0439\u043a\u043e\u0432\u044b\u0439 \u0430\u043a\u043a\u0430\u0443\u043d\u0442 \u0434\u043b\u044f \u0431\u043e\u0442\u0430. \u0416\u0434\u0438 \u0434\u0430\u043b\u044c\u043d\u0435\u0439\u0448\u0438\u0445 \u0438\u043d\u0441\u0442\u0440\u0443\u043a\u0446\u0438\u0439.");
            this.applicationManager.messageBox("\u0414\u043b\u044f \u0440\u0430\u0431\u043e\u0442\u044b \u0431\u043e\u0442\u0430 \u043d\u0443\u0436\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442.\n\u041f\u0435\u0440\u0435\u0439\u0434\u0438 \u043d\u0430 \u0432\u043a\u043b\u0430\u0434\u043a\u0443 \u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b \u0438 \u043d\u0430\u0436\u043c\u0438 \u043a\u043d\u043e\u043f\u043a\u0443 \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442.\n\u0412\u043e\u0439\u0434\u0438 \u0432 \u0441\u0432\u043e\u0439 \u0438\u043b\u0438 \u0444\u0435\u0439\u043a\u043e\u0432\u044b\u0439 \u0430\u043a\u043a\u0430\u0443\u043d\u0442 \u0434\u043b\u044f \u0431\u043e\u0442\u0430. \u0416\u0434\u0438 \u0434\u0430\u043b\u044c\u043d\u0435\u0439\u0448\u0438\u0445 \u0438\u043d\u0441\u0442\u0440\u0443\u043a\u0446\u0438\u0439.");
        }
    }

    @Override
    public String process(String string) {
        String string2 = "";
        for (int i = 0; i < this.size(); ++i) {
            string2 = string2 + ((VkAccount)this.get(i)).process(string);
        }
        for (int j = 0; j < this.commands.size(); ++j) {
            string2 = string2 + ((Command)this.commands.get(j)).process(string);
        }
        return string2;
    }

    public void removeAccount(final VkAccount vkAccount) {
        new Thread((Runnable)new Runnable(){

            public void run() {
                vkAccount.setActive(false);
                vkAccount.removeFile();
                AccountManager.this.remove((Object)vkAccount);
            }
        }).start();
    }

    private class AddAccount
    implements Command {
        private AddAccount() {
        }

        @Override
        public String getHelp() {
            return "[ \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0430\u043a\u043a\u0430\u0443\u043d\u0442 \u0432 \u0441\u043f\u0438\u0441\u043e\u043a \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432 ]\n---| botcmd account add <\u0442\u043e\u043a\u0435\u043d> <ID \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f>\n\n";
        }

        @Override
        public String process(String string) {
            CommandParser commandParser = new CommandParser(string);
            if (commandParser.getWord().equals((Object)"account") && commandParser.getWord().equals((Object)"add")) {
                String string2 = commandParser.getWord();
                long l = commandParser.getLong();
                AccountManager.this.addAccount(string2, l);
                return "\u0412 \u0441\u043f\u0438\u0441\u043e\u043a \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0430\u043a\u043a\u0430\u0443\u043d\u0442 \u0441 id = " + l + ", token = " + string2;
            }
            return "";
        }
    }

    private class Status
    implements Command {
        private Status() {
        }

        @Override
        public String getHelp() {
            return "";
        }

        @Override
        public String process(String string) {
            if (new CommandParser(string).getWord().equals((Object)"status")) {
                return "\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432: " + AccountManager.this.size() + "\n";
            }
            return "";
        }
    }

}

