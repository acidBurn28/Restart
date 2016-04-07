/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.fsoft.vktest;

import android.content.SharedPreferences;
import android.content.res.Resources;
import com.fsoft.vktest.AnswerDatabase;
import com.fsoft.vktest.ApplicationManager;
import com.fsoft.vktest.FunctionAnswerer;
import com.fsoft.vktest.HistoryProvider;
import com.fsoft.vktest.MessageComparer;
import com.fsoft.vktest.PatternProcessor;
import com.fsoft.vktest.ThematicsProcessor;
import com.fsoft.vktest.VkCommunicator;
import com.fsoft.vktest.common.Command;
import com.fsoft.vktest.common.CommandParser;
import com.fsoft.vktest.common.FileReader;
import com.fsoft.vktest.common.TimeCounter;
import com.fsoft.vktest.common.UserList;
import com.fsoft.vktest.windows.TabsActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IhaSmartProcessor
implements Command {
    boolean allTeachers = false;
    public UserList allowId;
    public AnswerDatabase answerDatabase;
    AnswerPhone answerPhone;
    ArrayList<Long> answeredAboutOwnerOnly = new ArrayList();
    ApplicationManager applicationManager = null;
    String botTreatment = "\u0431\u043e\u0442,";
    ArrayList<Command> commands;
    Filter filter;
    FunctionAnswerer functionAnswerer;
    HistoryProvider historyProvider;
    public UserList ignorId;
    String name;
    ThematicsProcessor negativeProcessor;
    PatternProcessor patternProcessor;
    ThematicsProcessor positiveProcessor;
    PostScriptumProcessor postScriptumProcessor;
    RepeatsProcessor repeatsProcessor;
    public UserList teachId;
    TimeCounter timeCounter;

    public IhaSmartProcessor(ApplicationManager applicationManager, String string) {
        this.applicationManager = applicationManager;
        this.name = string;
        this.commands = new ArrayList();
        this.answerDatabase = new AnswerDatabase(applicationManager, string, 2130968577);
        this.historyProvider = new HistoryProvider(applicationManager);
        this.positiveProcessor = new ThematicsProcessor(applicationManager, string, 2130968583, "pos");
        this.negativeProcessor = new ThematicsProcessor(applicationManager, string, 2130968581, "neg");
        this.patternProcessor = new PatternProcessor(applicationManager, string, 2130968582);
        this.functionAnswerer = new FunctionAnswerer(applicationManager, string);
        this.timeCounter = new TimeCounter();
        this.repeatsProcessor = new RepeatsProcessor();
        this.filter = new Filter();
        this.answerPhone = new AnswerPhone();
        this.postScriptumProcessor = new PostScriptumProcessor();
        this.allowId = new UserList("allow", applicationManager);
        this.ignorId = new UserList("ignor", applicationManager);
        this.teachId = new UserList("teacher", applicationManager);
        this.commands.add((Object)this.allowId);
        this.commands.add((Object)this.ignorId);
        this.commands.add((Object)this.teachId);
        this.commands.add((Object)this.filter);
        this.commands.add((Object)this.answerPhone);
        this.commands.add((Object)this.functionAnswerer);
        this.commands.add((Object)this.postScriptumProcessor);
        this.commands.add((Object)this.positiveProcessor);
        this.commands.add((Object)this.negativeProcessor);
        this.commands.add((Object)this.patternProcessor);
        this.commands.add((Object)this.historyProvider);
        this.commands.add((Object)this.answerDatabase);
        this.commands.add((Object)new Save());
        this.commands.add((Object)new Status());
        this.commands.add((Object)new SetBotTreatment());
        this.commands.add((Object)new SetAllTeachers());
    }

    private String log(String string) {
        ApplicationManager.log(string);
        return string;
    }

    public String addUserName(String string, Long l) {
        if (string == null || string.equals((Object)"")) {
            return string;
        }
        String string2 = this.applicationManager.vkCommunicator.getUserName(l);
        String[] arrstring = string2.split("\\ ");
        int n = arrstring.length;
        String string3 = null;
        if (n > 0) {
            string3 = arrstring[0];
        }
        if (string3 != null) {
            string = string3 + ", " + string;
        }
        this.log(". \u0417\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u043e \u0438\u043c\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f: " + string2 + " [" + arrstring.length + "] name = " + string3);
        return string;
    }

    public void close() {
        this.positiveProcessor.close();
        this.negativeProcessor.close();
        this.patternProcessor.close();
        this.functionAnswerer.close();
    }

    @Override
    public String getHelp() {
        String string = "";
        for (int i = 0; i < this.commands.size(); ++i) {
            string = string + ((Command)this.commands.get(i)).getHelp();
        }
        return string;
    }

    public void load() {
        String string = this.applicationManager.activity.getPreferences(0).getString("botTreatment", this.botTreatment);
        if (this.applicationManager.isDonated() || !string.equals((Object)"")) {
            this.botTreatment = string;
        }
        this.allowId.load();
        this.ignorId.load();
        this.teachId.load();
        this.answerDatabase.load();
        this.positiveProcessor.load();
        this.negativeProcessor.load();
        this.patternProcessor.load();
        this.functionAnswerer.load();
    }

    @Override
    public String process(String string) {
        String string2 = "";
        for (int i = 0; i < this.commands.size(); ++i) {
            string2 = string2 + ((Command)this.commands.get(i)).process(string);
        }
        return string2;
    }

    String processCommand(String string, Long l) {
        String string2 = string.replace((CharSequence)"Botcmd", (CharSequence)"botcmd");
        if (string2.toLowerCase().contains((CharSequence)"botcmd") && !string2.contains((CharSequence)"botcmd")) {
            return "\u041e\u0448\u0438\u0431\u043a\u0430: \u043f\u0440\u043e\u0432\u0435\u0440\u044c\u0442\u0435 \u0440\u0435\u0433\u0438\u0441\u0442\u0440 \u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432. \u0412\u0441\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u0434\u043e\u043b\u0436\u043d\u044b \u0431\u044b\u0442\u044c \u0432 \u043d\u0438\u0436\u043d\u0435\u043c \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0435.";
        }
        if (string2.contains((CharSequence)"botcmd")) {
            if (l.equals((Object)this.applicationManager.getUserID()) || this.allowId.contains(l) || l.equals((Object)10299185)) {
                String[] arrstring = string2.split("\\ ");
                if (arrstring.length >= 2) {
                    if (arrstring[1].equals((Object)"help")) {
                        return "\u041f\u043e\u043c\u043e\u0449\u044c \u043f\u043e \u043a\u043e\u043c\u0430\u043d\u0434\u0430\u043c \u043c\u043e\u0434\u0443\u043b\u0435\u0439: \n" + this.applicationManager.getCommandsHelp();
                    }
                    return this.applicationManager.processCommand(string2.replace((CharSequence)"botcmd ", (CharSequence)""));
                }
                return "\u0414\u043e\u043f\u0438\u0448\u0438\u0442\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u0443.\n";
            }
            if (!this.answeredAboutOwnerOnly.contains((Object)l)) {
                this.answeredAboutOwnerOnly.add((Object)l);
                return "\u041e\u0448\u0438\u0431\u043a\u0430: \u043e\u0431\u0440\u0430\u0431\u0430\u0442\u044b\u0432\u0430\u044e\u0442\u0441\u044f \u0442\u043e\u043b\u044c\u043a\u043e \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u0432\u043b\u0430\u0434\u0435\u043b\u044c\u0446\u0430 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u044b.\n";
            }
        }
        return "";
    }

    public String processMessage(String string, Long l) {
        if (!(!this.ignorId.contains(l) || this.allowId.contains(l) || this.applicationManager.getUserID().equals((Object)l))) {
            return null;
        }
        String string2 = this.processCommand(string, l);
        if (!this.applicationManager.isStandby()) {
            if (!this.teachId.contains(l)) {
                if (string2 == null || string2.equals((Object)"")) {
                    string2 = this.answerPhone.processMessage(string, l);
                }
                if (string2 == null || string2.equals((Object)"")) {
                    string2 = this.positiveProcessor.processMessage(string.toLowerCase().replace((CharSequence)this.botTreatment, (CharSequence)""), l);
                }
                if (string2 == null || string2.equals((Object)"")) {
                    string2 = this.negativeProcessor.processMessage(string.toLowerCase().replace((CharSequence)this.botTreatment, (CharSequence)""), l);
                }
                if (string2 == null || string2.equals((Object)"")) {
                    string2 = this.patternProcessor.processMessage(string, l);
                }
                if ((string2 == null || string2.equals((Object)"")) && string.toLowerCase().contains((CharSequence)this.botTreatment) && !this.allowId.contains(l) && !this.teachId.contains(l)) {
                    string2 = this.repeatsProcessor.processMessage(string.toLowerCase().replace((CharSequence)this.botTreatment, (CharSequence)"").trim(), l);
                }
                if ((string2 == null || string2.equals((Object)"")) && string.toLowerCase().contains((CharSequence)this.botTreatment)) {
                    string2 = this.functionAnswerer.processMessage(string.toLowerCase().replace((CharSequence)this.botTreatment, (CharSequence)"").trim(), l);
                }
            }
            if (string2 == null || string2.equals((Object)"")) {
                string2 = this.processSpeaking(string, l);
            }
            String string3 = this.processSpamFilter(string2, l);
            string2 = this.postScriptumProcessor.processMessage(string3, l);
        }
        String string4 = this.addUserName(string2, l);
        String string5 = this.filter.processMessage(string4, l);
        this.repeatsProcessor.registerBotAnswer(string5, l);
        return string5;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    String processSpamFilter(String string, Long l) {
        if (string == null) {
            return null;
        }
        int n = this.timeCounter.countLastSec(l, 120);
        this.timeCounter.add(l);
        if (n <= 6) return string;
        if (n <= 9) {
            return "\u0412\u044b \u043f\u0438\u0448\u0435\u0442\u0435 \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u043d\u043e\u0433\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439 \u0437\u0430 \u043c\u0438\u043d\u0443\u0442\u0443  (" + n + ")\n" + string;
        }
        if (n <= 11) {
            return "\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0438, \u043f\u0440\u0435\u0432\u044b\u0441\u0438\u0432\u0448\u0438\u0435 \u043b\u0438\u043c\u0438\u0442, \u0431\u043b\u043e\u043a\u0438\u0440\u0443\u044e\u0442\u0441\u044f.  (" + n + ")\n" + string;
        }
        if (n <= 11) return string;
        return "\u0412\u0430\u0448\u0430 \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u0430 \u0437\u0430\u0431\u043b\u043e\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u0430: " + this.applicationManager.processCommand(new StringBuilder().append("ignor add ").append((Object)l).append(" \u043f\u043e\u043f\u044b\u0442\u043a\u0430 \u0444\u043b\u0443\u0434\u0430").toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    String processSpeaking(String string, Long l) {
        if (!string.toLowerCase().contains((CharSequence)this.botTreatment)) {
            return null;
        }
        String string2 = string.toLowerCase().replace((CharSequence)this.botTreatment, (CharSequence)"").trim();
        String string3 = this.historyProvider.getLastMessage(l);
        long l2 = this.historyProvider.getLastMessageTime(l);
        long l3 = System.currentTimeMillis() - l2;
        String string4 = "";
        String string5 = "";
        if (this.teachId.contains(l) && this.answerDatabase.unknownMessages.size() > 0) {
            int n = 5;
            while (n > 0 && this.answerDatabase.unknownMessages.size() > 0) {
                ArrayList<String> arrayList;
                ArrayList<String> arrayList2 = arrayList = this.answerDatabase.unknownMessages;
                synchronized (arrayList2) {
                    String string6 = (String)this.answerDatabase.unknownMessages.get(0);
                    this.answerDatabase.unknownMessages.remove(0);
                    if (this.filter.isAllowed(string6)) {
                        if (!string4.equals((Object)"")) {
                            string4 = string4 + "\n---\n";
                        }
                        string4 = string4 + string6;
                        --n;
                    }
                    continue;
                }
            }
            string5 = string5 + "- \u041e\u0442\u0432\u0435\u0442\u044b \u043e\u0442\u043e\u0431\u0440\u0430\u043d\u044b \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 \u043d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u044b\u0445. \u041e\u0442\u0432\u0435\u0447\u0430\u0439\u0442\u0435 \u043d\u0430 \u043a\u0430\u0436\u0434\u043e\u0435 \u0441 \u043d\u043e\u0432\u043e\u0439 \u0441\u0442\u0440\u043e\u043a\u0438.\n- \u041e\u0441\u0442\u0430\u043b\u043e\u0441\u044c " + this.answerDatabase.unknownMessages.size() + " \u043d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u044b\u0445 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439\n" + "- \u0427\u0442\u043e\u0431\u044b \u043f\u0440\u043e\u043f\u0443\u0441\u0442\u0438\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435, \u043d\u0430\u043f\u0438\u0448\u0438\u0442\u0435 \"\u0434\u0430\u043b\u0435\u0435\" \u0438\u043b\u0438 \"\u043f\u0440\u043e\u043f\u0443\u0441\u0442\u0438\u0442\u044c\" \u0432 \u0441\u043e\u043e\u0442\u0432\u0435\u0442\u0441\u0442\u0432\u0443\u044e\u0449\u0435\u0439 \u0441\u0442\u0440\u043e\u0447\u043a\u0435.\n";
        } else {
            string4 = this.answerDatabase.getMaxValidAnswer(string2);
        }
        if ((this.teachId.contains(l) || this.allTeachers) && string3 != null && string3.length() > 0 && l3 < 180000 && string2.length() > 0) {
            String[] arrstring = string3.split("\n---\n");
            String[] arrstring2 = string2.split("\\\n");
            for (int i = 0; i < Math.min((int)arrstring.length, (int)arrstring2.length); ++i) {
                if (arrstring2[i].equals((Object)"\u0434\u0430\u043b\u0435\u0435") || arrstring2[i].equals((Object)"\u043f\u0440\u043e\u043f\u0443\u0441\u0442\u0438\u0442\u044c")) continue;
                string5 = string5 + "-" + this.answerDatabase.addToDatabase(arrstring[i], arrstring2[i]) + "\n";
            }
        }
        this.historyProvider.addMessage(l, string2);
        this.historyProvider.addMessage(l, string4);
        if (!string5.equals((Object)"")) {
            string4 = string4 + "\n----- \u0420\u0435\u0436\u0438\u043c \u0443\u0447\u0438\u0442\u0435\u043b\u044f -----\n" + string5;
        }
        return this.applicationManager.messageComparer.messagePreparer.processMessageBeforeShow(string4);
    }

    String save() {
        try {
            String string = "" + this.allowId.save();
            String string2 = string + this.teachId.save();
            String string3 = string2 + this.ignorId.save();
            SharedPreferences.Editor editor = this.applicationManager.activity.getPreferences(0).edit();
            editor.putString("botTreatment", this.botTreatment);
            editor.commit();
            String string4 = string3 + this.log(new StringBuilder().append(". \u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 ").append(this.botTreatment).append(" \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u043e.\n").toString());
            return string4;
        }
        catch (Exception var1_6) {
            var1_6.printStackTrace();
            return "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f \u0441\u043f\u0438\u0441\u043a\u043e\u0432 \u0434\u043e\u0432\u0435\u0440\u0435\u043d\u043d\u043e\u0441\u0442\u0438, \u0438\u0433\u043d\u043e\u0440\u0430 \u0438 \u0443\u0447\u0438\u0442\u0435\u043b\u0435\u0439: " + var1_6.toString() + " \n";
        }
    }

    class AnswerPhone
    implements Command {
        HashMap<Long, String> answers;

        AnswerPhone() {
            this.answers = new HashMap();
        }

        @Override
        public String getHelp() {
            return "[ \u041e\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044e \u0430\u0432\u0442\u043e\u043e\u0442\u0432\u0435\u0442\u0447\u0438\u043a ] \n---| botcmd setanswerphone <ID \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f> <\u0442\u0435\u043a\u0441\u0442 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f>\n\n[ \u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0430\u0432\u0442\u043e\u043e\u0442\u0432\u0435\u0442\u0447\u0438\u043a ] \n---| botcmd remanswerphone <ID \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f>\n\n[ \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0432\u0441\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0430\u0432\u0442\u043e\u043e\u0442\u0432\u0435\u0442\u0447\u0438\u043a\u0430 ] \n---| botcmd getanswerphone\n\n";
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public String process(String var1_1) {
            var2_2 = new CommandParser(var1_1);
            var3_3 = var2_2.getWord();
            var4_4 = -1;
            switch (var3_3.hashCode()) {
                case -81054002: {
                    if (var3_3.equals((Object)"setanswerphone")) {
                        var4_4 = 0;
                        ** break;
                    } else {
                        ** GOTO lbl12
                    }
                }
                case -479074410: {
                    if (var3_3.equals((Object)"remanswerphone")) {
                        var4_4 = 1;
                    }
                }
lbl12: // 7 sources:
                default: {
                    ** GOTO lbl17
                }
                case 1690682202: 
            }
            if (var3_3.equals((Object)"getanswerphone")) {
                var4_4 = 2;
            }
lbl17: // 4 sources:
            switch (var4_4) {
                default: {
                    return "";
                }
                case 0: {
                    var11_6 = IhaSmartProcessor.this.applicationManager.getUserID(var2_2.getWord());
                    var13_7 = var2_2.getText();
                    this.answers.put((Object)var11_6, (Object)var13_7);
                    return "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0430\u0432\u0442\u043e\u043e\u0442\u0432\u0435\u0442\u0447\u0438\u043a\u0430 \u0434\u043b\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f " + var11_6 + " \u043e\u0441\u0442\u0430\u0432\u043b\u0435\u043d\u043e: " + var13_7;
                }
                case 1: {
                    var8_8 = IhaSmartProcessor.this.applicationManager.getUserID(var2_2.getWord());
                    var10_9 = (String)this.answers.remove((Object)var8_8);
                    return "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0430\u0432\u0442\u043e\u043e\u0442\u0432\u0435\u0442\u0447\u0438\u043a\u0430 \u0434\u043b\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f " + var8_8 + " \u0443\u0434\u0430\u043b\u0435\u043d\u043e: " + var10_9;
                }
                case 2: 
            }
            var5_5 = "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0430\u0432\u0442\u043e\u0442\u0432\u0435\u0442\u0447\u0438\u043a\u043e\u0432 (" + this.answers.size() + ") :";
            var6_10 = this.answers.entrySet().iterator();
            while (var6_10.hasNext() != false) {
                var7_11 = (Map.Entry)var6_10.next();
                var5_5 = var5_5 + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 " + (String)var7_11.getValue() + " \u0434\u043b\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f " + var7_11.getKey() + "\n";
            }
            return var5_5;
        }

        String processMessage(String string, Long l) {
            if (this.answers.containsKey((Object)l)) {
                return (String)this.answers.get((Object)l);
            }
            return "";
        }
    }

    class Filter
    implements Command {
        private String allowedSymbols;
        private String[] allowedWords;
        private String[] fuckingWords;
        private String securityReport;
        private HashMap<Long, Integer> warnings;

        Filter() {
            this.warnings = new HashMap();
            this.fuckingWords = null;
            this.allowedSymbols = null;
            this.allowedWords = new String[]{"vk.com", "com. fsoft", "com. perm"};
            this.securityReport = "";
        }

        private boolean isAllowed(char c) {
            for (int i = 0; i < this.allowedSymbols.length(); ++i) {
                if (this.allowedSymbols.charAt(i) != c) continue;
                return true;
            }
            return false;
        }

        private void loadSymbols() {
            if (this.allowedSymbols == null) {
                this.allowedSymbols = new FileReader(IhaSmartProcessor.this.applicationManager.activity.getResources(), 2130968576, IhaSmartProcessor.this.name).readFile();
                IhaSmartProcessor.this.log(". \u0420\u0430\u0437\u0440\u0435\u0448\u0435\u043d\u043d\u044b\u0435 \u0441\u0438\u043c\u0432\u043e\u043b\u044b: \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u043e " + this.allowedSymbols.length() + " \u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432.");
            }
        }

        private void loadWords() {
            if (this.fuckingWords == null) {
                this.fuckingWords = new FileReader(IhaSmartProcessor.this.applicationManager.activity.getResources(), 2130968578, IhaSmartProcessor.this.name).readFile().split("\\\n");
                for (int i = 0; i < this.fuckingWords.length; ++i) {
                    this.fuckingWords[i] = this.fuckingWords[i].toLowerCase().replace((CharSequence)"|", (CharSequence)"");
                    this.fuckingWords[i] = this.replaceTheSameSymbols(this.fuckingWords[i]);
                }
                IhaSmartProcessor.this.log(". \u0427\u0435\u0440\u043d\u044b\u0439 \u0441\u043f\u0438\u0441\u043a\u043e\u043a: \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u043e " + this.fuckingWords.length + " \u0448\u0430\u0431\u043b\u043e\u043d\u043e\u0432.");
            }
        }

        private String passOnlyAllowedSymbols(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            this.loadSymbols();
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (!this.isAllowed(c)) continue;
                stringBuilder.append(c);
            }
            return stringBuilder.toString();
        }

        private String prepareToFilter(String string) {
            String string2 = " " + string.toLowerCase() + " ";
            for (int i = 0; i < this.allowedWords.length; ++i) {
                string2 = string2.replace((CharSequence)this.allowedWords[i], (CharSequence)"");
            }
            String string3 = this.replaceTheSameSymbols(string2);
            for (int j = 0; j < string3.length(); ++j) {
                char c = string3.charAt(j);
                boolean bl = false;
                for (int k = 0; k < " qwertyuiopasdfghjklzxcvbnm\u0439\u0446\u0443\u043a\u0435\u043d\u0433\u0448\u0449\u0437\u0445\u044a\u0444\u044b\u0432\u0430\u043f\u0440\u043e\u043b\u0434\u0436\u044d\u044f\u0447\u0441\u043c\u0438\u0442\u044c\u0431\u044e\u0456\u0454\u04511234567890".length(); ++k) {
                    if (c != " qwertyuiopasdfghjklzxcvbnm\u0439\u0446\u0443\u043a\u0435\u043d\u0433\u0448\u0449\u0437\u0445\u044a\u0444\u044b\u0432\u0430\u043f\u0440\u043e\u043b\u0434\u0436\u044d\u044f\u0447\u0441\u043c\u0438\u0442\u044c\u0431\u044e\u0456\u0454\u04511234567890".charAt(k)) continue;
                    bl = true;
                }
                if (bl) continue;
                string3 = string3.replace(c, ' ');
            }
            return string3.replaceAll(" +", " ");
        }

        private String replaceTheSameSymbols(String string) {
            return string.replace((CharSequence)"\u0443", (CharSequence)"y").replace((CharSequence)"\u043a", (CharSequence)"k").replace((CharSequence)"\u0435", (CharSequence)"e").replace((CharSequence)"\u043d", (CharSequence)"h").replace((CharSequence)"\u0437", (CharSequence)"3").replace((CharSequence)"\u0445", (CharSequence)"x").replace((CharSequence)"\u0432", (CharSequence)"b").replace((CharSequence)"\u0430", (CharSequence)"a").replace((CharSequence)"\u0440", (CharSequence)"p").replace((CharSequence)"\u043e", (CharSequence)"o").replace((CharSequence)"\u0441", (CharSequence)"c").replace((CharSequence)"\u043c", (CharSequence)"m").replace((CharSequence)"\u0438", (CharSequence)"n").replace((CharSequence)"\u0442", (CharSequence)"t").replace((CharSequence)"\u0456", (CharSequence)"i").replace((CharSequence)"\u044f", (CharSequence)"r");
        }

        @Override
        public String getHelp() {
            return "[ \u0421\u0431\u0440\u043e\u0441\u0438\u0442\u044c \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0441\u0447\u0435\u0442\u0447\u0438\u043a\u0430 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0439 \u0434\u043b\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f ]\n---| botcmd warning reset <id \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f>\n\n[ \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u044f \u0441\u0447\u0435\u0442\u0447\u0438\u043a\u0430 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0439 ]\n---| botcmd warning get\n\n[ \u0417\u0430\u0434\u0430\u0442\u044c \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0441\u0447\u0435\u0442\u0447\u0438\u043a\u0430 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0439 \u0434\u043b\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f ]\n---| botcmd warning set <id \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f> <\u043d\u043e\u0432\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0441\u0447\u0435\u0442\u0447\u0438\u043a\u0430>\n\n";
        }

        public boolean isAllowed(String string) {
            String string2 = this.prepareToFilter(string);
            this.loadWords();
            this.securityReport = "";
            boolean bl = false;
            for (int i = 0; i < this.fuckingWords.length; ++i) {
                if (!string2.contains((CharSequence)this.fuckingWords[i])) continue;
                this.securityReport = this.securityReport + IhaSmartProcessor.this.log(new StringBuilder().append("! \u0421\u0438\u0441\u0442\u0435\u043c\u0430 \u0437\u0430\u0449\u0438\u0442\u044b: \u043e\u0431\u043d\u0430\u0440\u0443\u0436\u0435\u043d \u043f\u043e\u0434\u043e\u0437\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u044b\u0439 \u0444\u0440\u0430\u0433\u043c\u0435\u043d\u0442: ").append(this.fuckingWords[i]).toString()) + "\n";
                bl = true;
            }
            if (!bl) {
                this.securityReport = ". \u0423\u0433\u0440\u043e\u0437 \u043d\u0435 \u043e\u0431\u043d\u0430\u0440\u0443\u0436\u0435\u043d\u043e.";
            }
            if (!bl) {
                return true;
            }
            return false;
        }

        /*
         * Exception decompiling
         */
        @Override
        public String process(String var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CASE], 4[SWITCH]], but top level block is 13[SWITCH]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:392)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:444)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2783)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:764)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:215)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:160)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:71)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:357)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:718)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:631)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:714)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:650)
            // org.benf.cfr.reader.Main.doJar(Main.java:109)
            // com.njlabs.showjava.AppProcessActivity$4.run(AppProcessActivity.java:423)
            // java.lang.Thread.run(Thread.java:864)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Enabled aggressive block sorting
         */
        public String processMessage(String string, long l) {
            if (string == null) return string;
            if (IhaSmartProcessor.this.allowId.contains(l)) return string;
            String string2 = this.passOnlyAllowedSymbols(string).replace((CharSequence)".", (CharSequence)". ").replace((CharSequence)"&#", (CharSequence)" ").replace((CharSequence)". . . ", (CharSequence)"...").replace((CharSequence)"vk. com", (CharSequence)"vk.com");
            if (this.isAllowed(string2)) return string2.trim();
            IhaSmartProcessor.this.applicationManager.messageBox("\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0434\u043b\u044f " + l + " (" + IhaSmartProcessor.this.applicationManager.vkCommunicator.getUserName(l) + ") \u043e\u043f\u0430\u0441\u043d\u043e.\n" + "--------------\n" + string2 + "\n" + "--------------\n" + this.securityReport);
            if (!this.warnings.containsKey((Object)l)) {
                this.warnings.put((Object)l, (Object)1);
                string2 = "\n\u0421\u0438\u0441\u0442\u0435\u043c\u0430 \u0437\u0430\u0449\u0438\u0442\u044b: \u0432\u0430\u0448\u0435 \u043f\u043e\u0432\u0435\u0434\u0435\u043d\u0438\u0435 \u0441\u043e\u043c\u043d\u0438\u0442\u0435\u043b\u044c\u043d\u043e. \n\u0415\u0441\u043b\u0438 \u044d\u0442\u043e \u043d\u0435 \u0442\u0430\u043a, \u0441\u043e\u043e\u0431\u0449\u0438\u0442\u0435 \u043f\u043e\u0434\u0440\u043e\u0431\u043d\u043e\u0441\u0442\u0438 \u0440\u0430\u0437\u0440\u0430\u0431\u043e\u0442\u0447\u0438\u043a\u0443.\n\u0412\u044b \u043f\u043e\u043b\u0443\u0447\u0430\u0435\u0442\u0435 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435 " + "1.";
                return string2.trim();
            }
            int n = 1 + (Integer)this.warnings.get((Object)l);
            this.warnings.put((Object)l, (Object)n);
            if (n >= 3) {
                String string3 = IhaSmartProcessor.this.applicationManager.processCommand("ignor add " + l + " \u043f\u043e\u0434\u043e\u0437\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0435 \u043f\u043e\u0432\u0435\u0434\u0435\u043d\u0438\u0435");
                string2 = "\u0412\u0430\u0448\u0430 \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u0430 \u0437\u0430\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043d\u0430: " + string3;
                return string2.trim();
            }
            string2 = "\n\u0421\u0438\u0441\u0442\u0435\u043c\u0430 \u0437\u0430\u0449\u0438\u0442\u044b: \u0432\u0430\u0448\u0435 \u043f\u043e\u0432\u0435\u0434\u0435\u043d\u0438\u0435 \u0441\u043e\u043c\u043d\u0438\u0442\u0435\u043b\u044c\u043d\u043e. \n\u0415\u0441\u043b\u0438 \u044d\u0442\u043e \u043d\u0435 \u0442\u0430\u043a, \u0441\u043e\u043e\u0431\u0449\u0438\u0442\u0435 \u043f\u043e\u0434\u0440\u043e\u0431\u043d\u043e\u0441\u0442\u0438 \u0440\u0430\u0437\u0440\u0430\u0431\u043e\u0442\u0447\u0438\u043a\u0443.\n\u0412\u044b \u043f\u043e\u043b\u0443\u0447\u0430\u0435\u0442\u0435 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435 " + n + ".";
            return string2.trim();
        }
    }

    class PostScriptumProcessor
    implements Command {
        ArrayList<Long> instructed;
        String instruction;

        PostScriptumProcessor() {
            this.instructed = new ArrayList();
            this.instruction = "";
        }

        @Override
        public String getHelp() {
            return "[ \u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u0442\u0435\u043a\u0441\u0442 \u043e\u0431\u044c\u044f\u0432\u043b\u0435\u043d\u0438\u044f P.S. ]\n---| botcmd setpsmessage <\u0442\u0435\u043a\u0441\u0442>\n\n[ \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u043f\u043e\u043b\u0443\u0447\u0438\u0432\u0448\u0438\u0445 \u043e\u0431\u044c\u044f\u0432\u043b\u0435\u043d\u0438\u0435 P.S. ]\n---| botcmd getpsreceivers\n\n";
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public String process(String var1_1) {
            var2_2 = new CommandParser(var1_1);
            var3_3 = var2_2.getWord();
            var4_4 = -1;
            switch (var3_3.hashCode()) {
                case -892481550: {
                    if (var3_3.equals((Object)"status")) {
                        var4_4 = 0;
                        ** break;
                    } else {
                        ** GOTO lbl12
                    }
                }
                case -1521366398: {
                    if (var3_3.equals((Object)"setpsmessage")) {
                        var4_4 = 1;
                    }
                }
lbl12: // 7 sources:
                default: {
                    ** GOTO lbl17
                }
                case -2127955797: 
            }
            if (var3_3.equals((Object)"getpsreceivers")) {
                var4_4 = 2;
            }
lbl17: // 4 sources:
            switch (var4_4) {
                default: {
                    return "";
                }
                case 0: {
                    return "\u041e\u0431\u044c\u044f\u0432\u043b\u0435\u043d\u0438\u0435 P.S. : " + this.instruction + "\n" + "\u041e\u0431\u044c\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u043f\u043e\u043b\u0443\u0447\u0438\u043b\u0438 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0435\u0439: " + this.instructed.size() + "\n";
                }
                case 1: {
                    this.instruction = var2_2.getText();
                    this.instructed.clear();
                    return "\u041e\u0431\u044c\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u043c\u043e\u0434\u0443\u043b\u044f P.S. = " + this.instruction;
                }
                case 2: 
            }
            var5_5 = "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 P.S. \u043f\u043e\u043b\u0443\u0447\u0438\u043b\u0438 " + this.instructed.size() + " \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0435\u0439:\n";
            for (var6_6 = 0; var6_6 < this.instructed.size(); ++var6_6) {
                var5_5 = var5_5 + " http://vk.com/id" + this.instructed.get(var6_6) + "\n";
            }
            return var5_5;
        }

        public String processMessage(String string, Long l) {
            if (!(string == null || string.equals((Object)"") || this.instruction.equals((Object)"") || this.instructed.contains((Object)l))) {
                this.instructed.add((Object)l);
                string = string + "\nP.S. " + this.instruction;
            }
            return string;
        }
    }

    class RepeatsProcessor {
        private HashMap<Long, String> lastBotMessages;
        private HashMap<Long, String> lastUserMessages;
        HashMap<Long, Integer> nervousCounters;

        RepeatsProcessor() {
            this.lastUserMessages = new HashMap();
            this.lastBotMessages = new HashMap();
            this.nervousCounters = new HashMap();
        }

        private int getNervous(Long l) {
            boolean bl = this.nervousCounters.containsKey((Object)l);
            int n = 0;
            if (bl) {
                n = (Integer)this.nervousCounters.get((Object)l);
            }
            return n;
        }

        private void incrementNervous(Long l) {
            this.nervousCounters.put((Object)l, (Object)(1 + this.getNervous(l)));
        }

        private void resetNervous(Long l) {
            this.nervousCounters.put((Object)l, (Object)0);
        }

        /*
         * Enabled aggressive block sorting
         */
        public String processMessage(String string, Long l) {
            String string2;
            boolean bl = this.lastBotMessages.containsKey((Object)l);
            boolean bl2 = false;
            String string3 = null;
            if (bl) {
                String string4 = (String)this.lastBotMessages.get((Object)l);
                bl2 = false;
                string3 = null;
                if (string4 != null) {
                    boolean bl3 = IhaSmartProcessor.this.applicationManager.messageComparer.isEquals(string4, string);
                    bl2 = false;
                    string3 = null;
                    if (bl3) {
                        int n = this.getNervous(l);
                        this.incrementNervous(l);
                        string3 = n < 10 ? "\u0422\u044b \u0447\u0451, \u043e\u0445\u0443\u0435\u043b, \u0431\u043b\u044f\u0442\u044c, \u0437\u0430 \u043c\u043d\u043e\u0439 \u043f\u043e\u0432\u0442\u043e\u0440\u044f\u0442\u044c?! (" + n + ")" : "\u0422\u044b, \u0441\u0443\u043a\u0430, \u0434\u043e\u0438\u0433\u0440\u0430\u043b\u0441\u044f: " + IhaSmartProcessor.this.applicationManager.processCommand(new StringBuilder().append("ignor add ").append((Object)l).append(" \u043f\u043e\u0432\u0442\u043e\u0440\u044f\u043b \u0437\u0430 \u0431\u043e\u0442\u043e\u043c").toString());
                        bl2 = true;
                    }
                }
            }
            if (this.lastUserMessages.containsKey((Object)l) && !bl2 && (string2 = (String)this.lastUserMessages.get((Object)l)) != null && !string2.equals((Object)"") && IhaSmartProcessor.this.applicationManager.messageComparer.isEquals(string2, string)) {
                int n = this.getNervous(l);
                this.incrementNervous(l);
                if (n < 3) {
                    string3 = "\u041d\u0435 \u043f\u043e\u0432\u0442\u043e\u0440\u044f\u0439\u0441\u044f. \u0411\u0435\u0441\u0438\u0442. (" + n + ")";
                } else if (n < 6) {
                    string3 = "\u0420\u0435\u0430\u043b\u044c\u043d\u043e \u0431\u0435\u0441\u0438\u0442! (" + n + ")";
                } else if (n < 9) {
                    string3 = "\u0422\u044b \u0434\u043e\u0441\u0442\u0430\u043b. \u042f \u0442\u0435\u0431\u044f \u0441\u0435\u0439\u0447\u0430\u0441 \u0437\u0430\u0431\u0430\u043d\u044e! (" + n + ")";
                } else if (n < 10) {
                    string3 = "\u0422\u044b \u0441\u0443\u043a\u0430 \u0434\u043e\u0438\u0433\u0440\u0430\u043b\u0441\u044f: " + IhaSmartProcessor.this.applicationManager.processCommand(new StringBuilder().append("ignor add ").append((Object)l).append(" \u043f\u043e\u0432\u0442\u043e\u0440\u044f\u043b\u0441\u044f").toString());
                }
                bl2 = true;
            }
            this.lastUserMessages.put((Object)l, (Object)string);
            if (!bl2) {
                this.resetNervous(l);
            }
            return string3;
        }

        public void registerBotAnswer(String string, Long l) {
            if (string != null) {
                this.lastBotMessages.put((Object)l, (Object)string);
            }
        }
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
                return IhaSmartProcessor.this.save() + "\n";
            }
            return "";
        }
    }

    class SetAllTeachers
    implements Command {
        SetAllTeachers() {
        }

        @Override
        public String getHelp() {
            return "[ \u0421\u0434\u0435\u043b\u0430\u0442\u044c \u0432\u0441\u0435\u0445 \u0443\u0447\u0438\u0442\u0435\u043b\u044f\u043c\u0438 ]\n---| botcmd setallteachers <on/off>\n\n";
        }

        @Override
        public String process(String string) {
            CommandParser commandParser = new CommandParser(string);
            if (commandParser.getWord().equals((Object)"setallteachers")) {
                boolean bl;
                StringBuilder stringBuilder = new StringBuilder().append("\u0412\u0441\u0435 \u0443\u0447\u0438\u0442\u0435\u043b\u044f: ");
                IhaSmartProcessor ihaSmartProcessor = IhaSmartProcessor.this;
                ihaSmartProcessor.allTeachers = bl = commandParser.getBoolean();
                return stringBuilder.append(bl).toString();
            }
            return "";
        }
    }

    class SetBotTreatment
    implements Command {
        SetBotTreatment() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String getHelp() {
            String string;
            StringBuilder stringBuilder = new StringBuilder().append("[ \u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043a \u0431\u043e\u0442\u0443 ]\n[ \u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \"EMPTY\" \u043e\u0442\u043a\u043b\u044e\u0447\u0438\u0442 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 ");
            if (IhaSmartProcessor.this.applicationManager.isDonated()) {
                string = "";
                do {
                    return stringBuilder.append(string).append(" ]\n").append("---| botcmd setbottreatment <\u043d\u043e\u0432\u043e\u0435 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435>\n\n").toString();
                    break;
                } while (true);
            }
            string = "(\u0434\u043e\u0441\u0442\u0443\u043f\u043d\u043e \u0442\u043e\u043b\u044c\u043a\u043e \u0432 \u043f\u043e\u043b\u043d\u043e\u0439 \u0432\u0435\u0440\u0441\u0438\u0438)";
            return stringBuilder.append(string).append(" ]\n").append("---| botcmd setbottreatment <\u043d\u043e\u0432\u043e\u0435 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435>\n\n").toString();
        }

        @Override
        public String process(String string) {
            CommandParser commandParser = new CommandParser(string);
            if (commandParser.getWord().equals((Object)"setbottreatment")) {
                String string2 = commandParser.getText().toLowerCase();
                if (string2.equals((Object)"") || string2.equals((Object)"empty")) {
                    if (IhaSmartProcessor.this.applicationManager.isDonated()) {
                        StringBuilder stringBuilder = new StringBuilder().append("\u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043a \u0431\u043e\u0442\u0443 \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u043e \u043d\u0430: \"");
                        IhaSmartProcessor.this.botTreatment = "";
                        return stringBuilder.append("").append("\".").toString();
                    }
                    return "\u041f\u0443\u0441\u0442\u043e\u0435 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043c\u043e\u0436\u043d\u043e \u0441\u0434\u0435\u043b\u0430\u0442\u044c \u0442\u043e\u043b\u044c\u043a\u043e \u0432 \u043f\u043e\u043b\u043d\u043e\u0439 \u0432\u0435\u0440\u0441\u0438\u0438";
                }
                if (!string2.contains((CharSequence)",")) {
                    string2 = string2 + ",";
                }
                StringBuilder stringBuilder = new StringBuilder().append("\u041e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0435 \u043a \u0431\u043e\u0442\u0443 \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u043e \u043d\u0430: \"");
                IhaSmartProcessor.this.botTreatment = string2;
                return stringBuilder.append(string2).append("\".").toString();
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
                return "\u0414\u043e\u0432\u0435\u0440\u0435\u043d\u043d\u044b\u0445 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u0439 \u0432 \u0431\u0430\u0437\u0435: " + IhaSmartProcessor.this.allowId.size() + " \n" + "\u0418\u0433\u043d\u043e\u0440\u0438\u0440\u0443\u0435\u043c\u044b\u0445 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u0439 \u0432 \u0431\u0430\u0437\u0435: " + IhaSmartProcessor.this.ignorId.size() + " \n" + "\u0423\u0447\u0438\u0442\u0435\u043b\u0435\u0439 \u0432 \u0431\u0430\u0437\u0435: " + IhaSmartProcessor.this.teachId.size() + " \n" + "\u0423\u0447\u0438\u0442\u0435\u043b\u044f\u043c\u0438 \u044f\u0432\u043b\u044f\u044e\u0442\u0441\u044f \u0432\u0441\u0435: " + IhaSmartProcessor.this.allTeachers + " \n";
            }
            return "";
        }
    }

}

