/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Random
 *  java.util.Set
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package com.fsoft.vktest;

import android.content.res.Resources;
import com.fsoft.vktest.ApplicationManager;
import com.fsoft.vktest.IhaSmartProcessor;
import com.fsoft.vktest.MessageComparer;
import com.fsoft.vktest.ThematicsProcessor;
import com.fsoft.vktest.VkCommunicator;
import com.fsoft.vktest.common.Command;
import com.fsoft.vktest.common.CommandParser;
import com.fsoft.vktest.common.FileReader;
import com.fsoft.vktest.common.TimeCounter;
import com.fsoft.vktest.windows.TabsActivity;
import com.perm.kate.api.WallMessage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FunctionAnswerer
implements Command {
    ApplicationManager applicationManager = null;
    ArrayList<Function> functions = new ArrayList();
    String name;

    public FunctionAnswerer(ApplicationManager applicationManager, String string) {
        this.applicationManager = applicationManager;
        this.name = string;
        this.functions.add((Object)new Anonymous());
        this.functions.add((Object)new Say());
        this.functions.add((Object)new Cities());
        this.functions.add((Object)new IliIli());
        this.functions.add((Object)new Infa());
        this.functions.add((Object)new When());
        this.functions.add((Object)new Time());
        this.functions.add((Object)new BanMe());
        this.functions.add((Object)new WhichDayOfMonth());
        this.functions.add((Object)new WhichDayOfWeek());
        this.functions.add((Object)new Rendom());
        this.functions.add((Object)new Cities());
        this.functions.add((Object)new PseudoGraphic());
        this.functions.add((Object)new MyMessages());
        this.functions.add((Object)new MathSolver());
    }

    private boolean isNegative(String string) {
        return this.applicationManager.messageProcessor.negativeProcessor.patterns.match(string);
    }

    private boolean isPositive(String string) {
        return this.applicationManager.messageProcessor.positiveProcessor.patterns.match(string);
    }

    private String log(String string) {
        ApplicationManager.log(string);
        return string;
    }

    public void close() {
    }

    @Override
    public String getHelp() {
        String string = "";
        for (int i = 0; i < this.functions.size(); ++i) {
            string = string + ((Function)this.functions.get(i)).getHelp();
        }
        return string;
    }

    public void load() {
    }

    @Override
    public String process(String string) {
        String string2 = "";
        for (int i = 0; i < this.functions.size(); ++i) {
            string2 = string2 + ((Function)this.functions.get(i)).process(string);
        }
        return string2;
    }

    public String processMessage(String string, Long l) {
        for (int i = 0; i < this.functions.size(); ++i) {
            String string2 = ((Function)this.functions.get(i)).process(string, l);
            if (string2 == null || string2.equals((Object)"")) continue;
            return string2;
        }
        return null;
    }

    class Anonymous
    extends Function {
        HashMap<Long, Integer> messagesSent;
        TimeCounter timeCounter;

        Anonymous() {
            super();
            this.timeCounter = new TimeCounter();
            this.messagesSent = new HashMap();
        }

        @Override
        public String process(String string) {
            String string2;
            if (new CommandParser(string).getWord().equals((Object)"status")) {
                string2 = "\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u043e\u0442\u043f\u0440\u0430\u0432\u043a\u0438 \u0430\u043d\u043e\u043d\u0438\u043c\u043d\u044b\u0445 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439:\n";
                for (Map.Entry entry : this.messagesSent.entrySet()) {
                    string2 = string2 + "- \u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c vk.com/id" + entry.getKey() + " \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u043b " + entry.getValue() + " \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439.\n";
                }
            } else {
                string2 = super.process(string);
            }
            return string2;
        }

        @Override
        String process(String string, Long l) {
            String[] arrstring = string.split("\\ ");
            if (arrstring.length > 2 && arrstring[0].toLowerCase().equals((Object)"\u0430\u043d\u043e\u043d\u0438\u043c\u043d\u043e")) {
                String string2 = "" + FunctionAnswerer.this.log(". \u041e\u0442\u043f\u0440\u0430\u0432\u043a\u0430 \u0430\u043d\u043e\u043d\u0438\u043c\u043d\u043e\u0433\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f...\n");
                String string3 = arrstring[1];
                String string4 = string2 + FunctionAnswerer.this.log(new StringBuilder().append(". \u041f\u043e\u043b\u0443\u0447\u0435\u043d\u043e \u0438\u043c\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f: ").append(string3).append("\n").toString());
                long l2 = FunctionAnswerer.this.applicationManager.getUserID(string3);
                String string5 = string4 + FunctionAnswerer.this.log(new StringBuilder().append(". \u041f\u043e\u043b\u0443\u0447\u0435\u043d\u043e id \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f: ").append(l2).append("\n").toString());
                if (l2 < 2) {
                    return string5 + FunctionAnswerer.this.log("! id \u043d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u0435\u043d.\n");
                }
                if (l2 == 100 || l2 == 101 || l2 == 333) {
                    return string5 + FunctionAnswerer.this.log("! \u0412 \u043f\u0438\u0437\u0434\u0443 \u0441\u0435\u0431\u0435 \u043e\u0442\u043f\u0440\u0430\u0432\u044c \u0442\u0430\u043a\u043e\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435, \u0430 \u043d\u0435 \u0430\u0433\u0435\u043d\u0442\u0443 \u043f\u043e\u0434\u0434\u0435\u0440\u0436\u043a\u0438!.\n");
                }
                String string6 = string.replace((CharSequence)arrstring[0], (CharSequence)"").replace((CharSequence)arrstring[1], (CharSequence)"").trim();
                String string7 = string5 + FunctionAnswerer.this.log(new StringBuilder().append(". \u041f\u043e\u043b\u0443\u0447\u0435\u043d \u0442\u0435\u043a\u0441\u0442 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f: ").append(string6).append("\n").toString());
                String string8 = FunctionAnswerer.this.applicationManager.messageProcessor.filter.processMessage(string6, l);
                String string9 = string7 + FunctionAnswerer.this.log(new StringBuilder().append(". \u0422\u0435\u043a\u0441\u0442 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 \u0444\u0438\u043b\u044c\u0442\u0440\u0430\u0446\u0438\u0438: ").append(string8).append("\n").toString());
                if (!string8.contains((CharSequence)"\u0412\u044b \u043f\u043e\u043b\u0443\u0447\u0430\u0435\u0442\u0435 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435")) {
                    boolean bl = this.messagesSent.containsKey((Object)l);
                    int n = 0;
                    if (bl) {
                        n = (Integer)this.messagesSent.get((Object)l);
                    }
                    if (this.timeCounter.countLastSec(l, 18000) < 5) {
                        String string10 = ApplicationManager.botMark() + "\u0412\u044b \u043f\u043e\u043b\u0443\u0447\u0438\u043b\u0438 \u0430\u043d\u043e\u043d\u0438\u043c\u043d\u043e\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435: " + string8 + "\n" + "--------------------\n" + "\u041d\u0435 \u043d\u0443\u0436\u043d\u043e \u043e\u0442\u0432\u0435\u0447\u0430\u0442\u044c \u043d\u0430 \u044d\u0442\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435, \u0442.\u043a. \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u0442\u0435\u043b\u044c \u043d\u0435 \u0441\u043c\u043e\u0436\u0435\u0442 \u043f\u0440\u043e\u0447\u0438\u0442\u0430\u0442\u044c \u0412\u0430\u0448 \u043e\u0442\u0432\u0435\u0442.";
                        String string11 = string9 + FunctionAnswerer.this.applicationManager.vkCommunicator.sendMessage(l2, string10);
                        this.timeCounter.add(l);
                        int n2 = n + 1;
                        this.messagesSent.put((Object)l, (Object)n2);
                        return string11;
                    }
                    return string9 + FunctionAnswerer.this.log(". \u0412\u044b \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u043b\u0438 \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u043d\u043e\u0433\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439 \u0437\u0430 \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u0435\u0435 \u0432\u0440\u0435\u043c\u044f. \u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439\u0442\u0435 \u043f\u043e\u0437\u0436\u0435.\n! \u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043d\u0435 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u043e.\n");
                }
                return string8 + "\n " + FunctionAnswerer.this.log("! \u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043d\u0435 \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u043e.\n");
            }
            return super.process(string, l);
        }
    }

    class BanMe
    extends Function {
        BanMe() {
            super();
        }

        @Override
        String process(String string, Long l) {
            if (string.contains((CharSequence)"\u0437\u0430\u0431\u0430\u043d\u044c \u043c\u0435\u043d\u044f")) {
                String string2 = FunctionAnswerer.this.applicationManager.processCommand("ignor add " + (Object)l + " \u043f\u043e \u0441\u043e\u0431\u0441\u0442\u0432\u0435\u043d\u043d\u043e\u043c\u0443 \u0436\u0435\u043b\u0430\u043d\u0438\u044e");
                return "\u041e\u043a, " + string2;
            }
            return super.process(string, l);
        }
    }

    class Cities
    extends Function {
        String[] cities;
        FileReader fileReader;
        HashMap<Long, ArrayList<String>> gameHistory;

        Cities() {
            super();
            this.fileReader = new FileReader(FunctionAnswerer.this.applicationManager.activity.getResources(), 2130968580, FunctionAnswerer.this.name);
            this.cities = null;
            this.gameHistory = new HashMap();
        }

        private char getFirstLetter(String string) {
            for (int i = 0; i < string.length(); ++i) {
                if (string.charAt(i) == ' ') continue;
                return string.charAt(i);
            }
            return 'a';
        }

        private char getLastLetter(String string) {
            for (int i = -1 + string.length(); i >= 0; --i) {
                if (string.charAt(i) == ' ' || string.charAt(i) == '.' || string.charAt(i) == '\u044b' || string.charAt(i) == '\u044c' || string.charAt(i) == '\u0451' || string.charAt(i) == '\u044a') continue;
                return string.charAt(i);
            }
            return 'a';
        }

        private String getNextCityOnLetter(char c, long l) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.cities.length; ++i) {
                if (this.getFirstLetter(this.cities[i]) != c) continue;
                arrayList.add((Object)this.cities[i]);
            }
            ArrayList arrayList2 = (ArrayList)this.gameHistory.get((Object)l);
            for (int j = 0; j < arrayList2.size(); ++j) {
                if (!arrayList.contains(arrayList2.get(j))) continue;
                arrayList.remove(arrayList2.get(j));
            }
            if (arrayList.size() == 0) {
                return "\u041d\u0430 \u044d\u0442\u0443 \u0431\u0443\u043a\u0432\u0443 \u0431\u043e\u043b\u044c\u0448\u0435 \u0433\u043e\u0440\u043e\u0434\u043e\u0432 \u043d\u0435\u0442. \u041f\u043e\u0442\u043e\u043c\u0443 \u0434\u0430\u0432\u0430\u0439 \u0447\u0442\u043e \u043d\u0438\u0434\u0443\u0434\u044c \u043d\u0430 \u0431\u0443\u043a\u0432\u0443 \u043d.";
            }
            return (String)arrayList.get(new Random().nextInt(arrayList.size()));
        }

        private void initCities() {
            this.cities = this.fileReader.readFile().split("\\\n");
            for (int i = 0; i < this.cities.length; ++i) {
                this.cities[i] = this.prepareCityName(this.cities[i]);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean isCity(String string) {
            if (string.length() < 4) {
                return false;
            }
            for (int i = 0; i < this.cities.length; ++i) {
                if (!this.cities[i].contains((CharSequence)string)) continue;
                return true;
            }
            return false;
        }

        private String prepareCityName(String string) {
            return (" " + string + " ").toLowerCase().replace((CharSequence)"!", (CharSequence)" ").replace((CharSequence)"?", (CharSequence)" ").replace((CharSequence)"-", (CharSequence)" ").replace((CharSequence)",", (CharSequence)" ").replace((CharSequence)".", (CharSequence)" ").replace((CharSequence)"_", (CharSequence)" ").replaceAll(" +", " ");
        }

        @Override
        String process(String string, Long l) {
            if (this.gameHistory.containsKey((Object)l)) {
                String string2 = this.prepareCityName(string);
                if (string2.toLowerCase().trim().equals((Object)"\u0441\u0442\u043e\u043f \u0438\u0433\u0440\u0430") || string2.toLowerCase().contains((CharSequence)"\u043a\u043e\u043d\u0435\u0446 \u0438\u0433\u0440\u044b") || string2.toLowerCase().contains((CharSequence)"\u0437\u0430\u043a\u043e\u043d\u0447\u0438\u0442\u044c \u0438\u0433\u0440\u0443") || string2.toLowerCase().contains((CharSequence)"\u0437\u0430\u0432\u0435\u0440\u0448\u0438\u0442\u044c \u0438\u0433\u0440\u0443") || string2.toLowerCase().contains((CharSequence)"\u044f \u043d\u0435 \u0438\u0433\u0440\u0430\u044e") || string2.toLowerCase().contains((CharSequence)"\u0445\u0432\u0430\u0442\u0438\u0442 \u0438\u0433\u0440\u0430\u0442\u044c") || string2.toLowerCase().contains((CharSequence)"\u0438\u0434\u0438 \u043d\u0430\u0445\u0443\u0439") || string2.toLowerCase().contains((CharSequence)"\u043f\u043e\u0448\u0435\u043b \u043d\u0430\u0445\u0443\u0439") || string2.toLowerCase().contains((CharSequence)"\u0438\u0434\u0438 \u0432 \u0436\u043e\u043f\u0443") || string2.toLowerCase().contains((CharSequence)"\u043f\u043e\u0448\u0435\u043b \u0432 \u0436\u043e\u043f\u0443")) {
                    int n = ((ArrayList)this.gameHistory.get((Object)l)).size();
                    this.gameHistory.remove((Object)l);
                    return "\u0438\u0433\u0440\u0430 \u0437\u0430\u043a\u043e\u043d\u0447\u0435\u043d\u0430! \u0421 \u0442\u043e\u0431\u043e\u0439 \u043f\u0440\u0438\u044f\u0442\u043d\u043e \u0431\u044b\u043b\u043e \u0438\u0433\u0440\u0430\u0442\u044c. \u041c\u044b \u0441\u044b\u0433\u0440\u0430\u043b\u0438 " + n + " \u0433\u043e\u0440\u043e\u0434\u043e\u0432.";
                }
                if (this.cities == null) {
                    this.initCities();
                }
                if (this.isCity(string2)) {
                    char c;
                    ArrayList arrayList = (ArrayList)this.gameHistory.get((Object)l);
                    if (arrayList.contains((Object)string2)) {
                        return "\u0431\u044b\u043b\u043e \u0443\u0436\u0435.";
                    }
                    if (arrayList.size() > 0 && (c = this.getLastLetter((String)arrayList.get(-1 + arrayList.size()))) != this.getFirstLetter(string2)) {
                        return "\u043d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e. \u0422\u0435\u0431\u0435 \u0441\u0435\u0439\u0447\u0430\u0441 \u043d\u0443\u0436\u043d\u043e \u0433\u043e\u0432\u043e\u0440\u0438\u0442\u044c \u0433\u043e\u0440\u043e\u0434 \u043d\u0430 \u0431\u0443\u043a\u0432\u0443 \"" + c + "\".";
                    }
                    arrayList.add((Object)string2);
                    String string3 = this.getNextCityOnLetter(this.getLastLetter(string2), l);
                    arrayList.add((Object)string3);
                    return FunctionAnswerer.this.applicationManager.messageComparer.messagePreparer.makeBeginWithUpper(string3.trim());
                }
                return "\u044f \u043d\u0435 \u0437\u043d\u0430\u044e \u0442\u0430\u043a\u043e\u0433\u043e \u0433\u043e\u0440\u043e\u0434\u0430. \u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439 \u043a\u0430\u043a\u043e\u0439-\u043d\u0438\u0431\u0443\u0434\u044c \u0434\u0440\u0443\u0433\u043e\u0439.";
            }
            if (string.toLowerCase().contains((CharSequence)"\u0433\u043e\u0440\u043e\u0434\u0430")) {
                ArrayList arrayList = new ArrayList();
                this.gameHistory.put((Object)l, (Object)arrayList);
                return "\u043d\u0443 \u0434\u0430\u0432\u0430\u0439 \u0441\u044b\u0433\u0440\u0430\u0435\u043c \u0432 \"\u0413\u043e\u0440\u043e\u0434\u0430\"! \u041f\u0440\u0430\u0432\u0438\u043b\u0430 \u043f\u0440\u043e\u0441\u0442\u044b\u0435: \u043d\u0443\u0436\u043d\u043e \u043d\u0430\u0437\u0432\u0430\u0442\u044c \u0433\u043e\u0440\u043e\u0434 \u043d\u0430 \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u044e\u044e \u0431\u0443\u043a\u0432\u0443 \u043f\u0440\u0435\u0434\u044b\u0434\u0443\u0449\u0435\u0433\u043e \u043d\u0430\u0437\u0432\u0430\u043d\u043e\u0433\u043e \u0433\u043e\u0440\u043e\u0434\u0430. \u041d\u0430\u0447\u0438\u043d\u0430\u0439 \u0442\u044b! \u041d\u0430\u0437\u044b\u0432\u0430\u0439 \u0433\u043e\u0440\u043e\u0434.\n \u0427\u0442\u043e\u0431\u044b \u0437\u0430\u043a\u043e\u043d\u0447\u0438\u0442\u044c \u0438\u0433\u0440\u0443, \u043d\u0430\u0431\u0435\u0440\u0438 \"\u0441\u0442\u043e\u043f \u0438\u0433\u0440\u0430\" \u0438\u043b\u0438 \"\u043a\u043e\u043d\u0435\u0446 \u0438\u0433\u0440\u044b\" \u0438\u043b\u0438 \"\u0437\u0430\u043a\u043e\u043d\u0447\u0438\u0442\u044c \u0438\u0433\u0440\u0443\".";
            }
            return super.process(string, l);
        }
    }

    class Function
    implements Command {
        Function() {
        }

        @Override
        public String getHelp() {
            return "";
        }

        @Override
        public String process(String string) {
            return "";
        }

        String process(String string, Long l) {
            return "";
        }
    }

    class IliIli
    extends Function {
        IliIli() {
        }

        @Override
        String process(String string, Long l) {
            if (string.contains((CharSequence)" \u0438\u043b\u0438 ")) {
                String string2 = string.replace((CharSequence)"!", (CharSequence)"").replace((CharSequence)"?", (CharSequence)"").replace((CharSequence)"\u0447\u0442\u043e \u043b\u0443\u0447\u0448\u0435", (CharSequence)"").replace((CharSequence)"\u0447\u0442\u043e \u043a\u0440\u0443\u0447\u0435", (CharSequence)"").replace((CharSequence)"\u0447\u0442\u043e \u043a\u0443\u043f\u0438\u0442\u044c", (CharSequence)"").replace((CharSequence)"\u0447\u0442\u043e \u0432\u044b\u0431\u0440\u0430\u0442\u044c", (CharSequence)"").replace((CharSequence)"\u0432\u044b\u0431\u0435\u0440\u0438", (CharSequence)"").replace((CharSequence)":", (CharSequence)"").replace((CharSequence)",", (CharSequence)"");
                MessageComparer messageComparer = FunctionAnswerer.this.applicationManager.messageComparer;
                Random random = new Random(System.currentTimeMillis());
                String[] arrstring = string2.split("\u0438\u043b\u0438");
                FunctionAnswerer.this.log("vars = " + arrstring.length);
                if (arrstring.length < 2) {
                    return "\u0438\u043b\u0438 \u0447\u0442\u043e?";
                }
                String[] arrstring2 = new String[]{"\u0430\u0439\u0444\u043e\u043d", "\u0430\u0439\u043f\u0430\u0434", "\u0430\u0439\u0442\u044e\u043d\u0441", "\u043c\u0430\u043a\u0431\u0443\u043a", "\u0430\u0439\u043e\u0441", "\u0430\u0439\u043f\u043e\u0434", "\u044d\u043f\u043f\u043b", "\u0433\u043e\u0432\u043d\u043e", "\u043a\u043e\u043d\u0441\u043e\u043b\u044c", "xbox", "playstation", "\u0430\u043d\u0438\u043c\u0435", "\u043f\u0443\u0442\u0438\u043d"};
                String string3 = null;
                for (int i = 0; i < arrstring.length; ++i) {
                    for (int j = 0; j < arrstring2.length; ++j) {
                        if (!messageComparer.isEquals(arrstring[i], arrstring2[j])) continue;
                        string3 = arrstring[i];
                    }
                }
                if (string3 != null) {
                    return "\u041e\u0434\u043d\u043e\u0437\u043d\u0430\u0447\u043d\u043e, " + string3 + " - \u0433\u043e\u0432\u043d\u043e.";
                }
                int n = random.nextInt(arrstring.length);
                return "\u041c\u043e\u0439 \u0432\u044b\u0431\u043e\u0440: " + arrstring[n];
            }
            return super.process(string, l);
        }
    }

    class Infa
    extends Function {
        Infa() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        String process(String string, Long l) {
            String[] arrstring = string.split("\\ ");
            if (arrstring.length <= 1) return super.process(string, l);
            if (!arrstring[0].toLowerCase().equals((Object)"\u0438\u043d\u0444\u0430")) return super.process(string, l);
            int n = (int)(System.currentTimeMillis() % 101);
            String string2 = string.replace((CharSequence)"\u0438\u043d\u0444\u0430", (CharSequence)"");
            if (FunctionAnswerer.this.isNegative(string2)) {
                n = 0;
                return "\u0418\u043d\u0444\u0430 \"" + string2 + "\" : " + n + "%.";
            }
            if (!FunctionAnswerer.this.isPositive(string2)) return "\u0418\u043d\u0444\u0430 \"" + string2 + "\" : " + n + "%.";
            n = 100;
            return "\u0418\u043d\u0444\u0430 \"" + string2 + "\" : " + n + "%.";
        }
    }

    class MathSolver
    extends Function {
        MathSolver() {
        }

        @Override
        String process(String string, Long l) {
            if (!(string.contains((CharSequence)"+") || string.contains((CharSequence)"-") || string.contains((CharSequence)"*") || string.contains((CharSequence)"/") || string.contains((CharSequence)"(") || string.contains((CharSequence)")") || string.contains((CharSequence)"^"))) {
                return "";
            }
            try {
                Expression expression = new ExpressionBuilder(string.toLowerCase().replace((CharSequence)"\u0441\u043a\u043e\u043b\u044c\u043a\u043e \u0431\u0443\u0434\u0435\u0442", (CharSequence)"").replace((CharSequence)"\u0440\u0435\u0448\u0438 \u043c\u043d\u0435", (CharSequence)"").replace((CharSequence)"\u0440\u0435\u0448\u0438", (CharSequence)"").replace((CharSequence)"\u0441\u043a\u043e\u043b\u044c\u043a\u043e", (CharSequence)"").replace((CharSequence)"\u043c\u043e\u0436\u0435\u0448\u044c \u0440\u0435\u0448\u0438\u0442\u044c", (CharSequence)"").replace((CharSequence)"\u043f\u043e\u0441\u0447\u0438\u0442\u0430\u0439", (CharSequence)"").replace((CharSequence)"\u0432\u044b\u0447\u0438\u0441\u043b\u0438", (CharSequence)"").replace((CharSequence)"\u043f\u043e\u043c\u043e\u0433\u0438", (CharSequence)"").replace((CharSequence)":", (CharSequence)"").replace((CharSequence)"?", (CharSequence)"").replace((CharSequence)"\u043f\u043b\u044e\u0441", (CharSequence)"+").replace((CharSequence)"\u043c\u0438\u043d\u0443\u0441", (CharSequence)"-").replace((CharSequence)"\u0440\u0430\u0437\u0434\u0435\u043b\u0438\u0442\u044c \u043d\u0430", (CharSequence)"/").replace((CharSequence)"\u00f7", (CharSequence)"/").replace((CharSequence)"\\", (CharSequence)"/").replace((CharSequence)"\u0443\u043c\u043d\u043e\u0436\u0438\u0442\u044c \u043d\u0430", (CharSequence)"*").replace((CharSequence)"\u0443\u043c\u043d\u043e\u0436\u0438\u0442\u044c", (CharSequence)"*").replace((CharSequence)"x", (CharSequence)"*").replace((CharSequence)"\u0445", (CharSequence)"*").replace((CharSequence)"\u00d7", (CharSequence)"*").replace((CharSequence)"\u0432 \u0441\u0442\u0435\u043f\u0435\u043d\u0438", (CharSequence)"^").replace((CharSequence)"\u0441\u0442\u0435\u043f\u0435\u043d\u044c", (CharSequence)"^").replace((CharSequence)"\u00b2", (CharSequence)"^2").replace((CharSequence)"\u00b3", (CharSequence)"^3").replaceAll(" ", "")).build();
                String string2 = ("\u0420\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442: " + String.valueOf((double)expression.evaluate())).replace('.', ',');
                String string3 = (string2 + ".").replace((CharSequence)",0.", (CharSequence)".");
                return string3;
            }
            catch (Exception var3_6) {
                return "";
            }
        }
    }

    class MyMessages
    extends Function {
        private HashMap<Long, Integer> userUsages;
        private VkCommunicator vkCommunicator;

        MyMessages() {
            this.userUsages = new HashMap();
        }

        static /* synthetic */ VkCommunicator access$100(MyMessages myMessages) {
            return myMessages.vkCommunicator;
        }

        String getLinks(ArrayList<WallMessage> arrayList, long l) {
            String string = "";
            for (int i = 0; i < arrayList.size(); ++i) {
                string = string + "http://vk.com/wall" + l + "_" + ((WallMessage)arrayList.get((int)i)).id + "\n";
            }
            return string;
        }

        void informate(String string, long l) {
            this.vkCommunicator.sendMessage(l, string);
        }

        @Override
        public String process(String string) {
            String string2;
            if (new CommandParser(string).getWord().equals((Object)"status")) {
                string2 = "\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f \u0444\u0443\u043d\u043a\u0446\u0438\u0438 \"\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043d\u0430 \u0441\u0442\u0435\u043d\u0435 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f\":\n";
                for (Map.Entry entry : this.userUsages.entrySet()) {
                    string2 = string2 + "- \u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c vk.com/id" + entry.getKey() + " \u0432\u044b\u043f\u043e\u043b\u043d\u0438\u043b " + entry.getValue() + " \u043f\u043e\u0438\u0441\u043a\u043e\u0432.\n";
                }
            } else {
                string2 = super.process(string);
            }
            return string2;
        }

        /*
         * Exception decompiling
         */
        @Override
        String process(String var1_1, Long var2_2) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // java.util.ConcurrentModificationException
            // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
            // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:483)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:600)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:610)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:774)
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

        void startFinding(final long l, final long l2, final long l3, final int n) {
            this.vkCommunicator = FunctionAnswerer.this.applicationManager.vkCommunicator;
            new Thread((Runnable)new Runnable(){

                /*
                 * Exception decompiling
                 */
                public void run() {
                    // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                    // java.util.ConcurrentModificationException
                    // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
                    // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:483)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:600)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:610)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:774)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:215)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:160)
                    // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:71)
                    // org.benf.cfr.reader.entities.Method.analyse(Method.java:357)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:718)
                    // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:631)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:714)
                    // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:631)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:714)
                    // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:650)
                    // org.benf.cfr.reader.Main.doJar(Main.java:109)
                    // com.njlabs.showjava.AppProcessActivity$4.run(AppProcessActivity.java:423)
                    // java.lang.Thread.run(Thread.java:864)
                    throw new IllegalStateException("Decompilation failed");
                }
            }).start();
        }

    }

    class PseudoGraphic
    extends Function {
        char empty;
        char filled;
        ArrayList<Symbol> symbols;

        PseudoGraphic() {
            this.symbols = null;
        }

        private String[] getLetter(char c) {
            if (this.symbols == null) {
                this.loadLetters();
            }
            for (int i = 0; i < this.symbols.size(); ++i) {
                if (!((Symbol)this.symbols.get(i)).isIt(c)) continue;
                return ((Symbol)this.symbols.get(i)).getLines();
            }
            return new String[]{"     ", "     ", "     ", "     ", "     "};
        }

        private String getText(String string) {
            String[] arrstring = new String[5];
            for (int i = 0; i < arrstring.length; ++i) {
                arrstring[i] = "";
            }
            for (int j = 0; j < string.length(); ++j) {
                String[] arrstring2 = this.getLetter(string.charAt(j));
                for (int k = 0; k < arrstring.length; ++k) {
                    arrstring[k] = arrstring[k] + " " + arrstring2[k];
                }
            }
            for (int k = 0; k < arrstring.length; ++k) {
                arrstring[k] = arrstring[k].replace(' ', this.empty).replace('#', this.filled);
            }
            String string2 = "\n";
            for (int i2 = 0; i2 < arrstring.length; ++i2) {
                string2 = string2 + arrstring[i2] + "\n";
            }
            return string2;
        }

        private void loadLetters() {
            this.symbols = new ArrayList();
            String[] arrstring = new FileReader(FunctionAnswerer.this.applicationManager.activity.getResources(), 2130968585, FunctionAnswerer.this.name).readFile().split("NS:");
            for (int i = 0; i < arrstring.length; ++i) {
                String[] arrstring2 = arrstring[i].split("\\\n");
                if (arrstring2.length < 6) continue;
                ArrayList<Symbol> arrayList = this.symbols;
                String string = arrstring2[0];
                String[] arrstring3 = new String[]{arrstring2[1], arrstring2[2], arrstring2[3], arrstring2[4], arrstring2[5]};
                arrayList.add((Object)new Symbol(string, arrstring3));
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        String process(String string, Long l) {
            CommandParser commandParser = new CommandParser(string);
            if (!commandParser.getWord().toLowerCase().equals((Object)"\u043d\u0430\u043f\u0438\u0448\u0438")) return super.process(string, l);
            this.filled = 9608;
            this.empty = 9618;
            String string2 = commandParser.getText().toLowerCase();
            Matcher matcher = Pattern.compile((String)"(\u0441\u0438\u043c\u0432\u043e\u043b\u044b(..) )").matcher((CharSequence)string2);
            if (matcher.find()) {
                String string3 = matcher.group(1);
                String string4 = matcher.group(2);
                string2 = string2.replace((CharSequence)string3, (CharSequence)"");
                this.filled = string4.charAt(0);
                this.empty = string4.charAt(1);
            }
            if (!FunctionAnswerer.this.applicationManager.messageProcessor.filter.isAllowed(string2)) {
                return "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 " + string2 + " \u043d\u0435 \u043c\u043e\u0436\u0435\u0442 \u0431\u044b\u0442\u044c \u043f\u043e\u043a\u0430\u0437\u0430\u043d\u043e.";
            }
            if (string2.length() < 1) {
                return "\u0412\u044b \u0437\u0430\u0431\u044b\u043b\u0438 \u043d\u0430\u043f\u0438\u0441\u0430\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043f\u043e\u0441\u043b\u0435 \u0441\u043b\u043e\u0432\u0430 \"\u043d\u0430\u043f\u0438\u0448\u0438\".";
            }
            if (string2.length() > 5000) {
                return "\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u0434\u043b\u0438\u043d\u043d\u043e\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435";
            }
            if (FunctionAnswerer.this.isNegative(string2)) {
                return "\u042f \u043d\u0435 \u0431\u0443\u0434\u0443 \u044d\u0442\u043e\u0433\u043e \u043f\u0438\u0441\u0430\u0442\u044c!";
            }
            String[] arrstring = string2.split("\\\\n");
            String string5 = "\u0422\u0435\u043a\u0441\u0442 " + string2.replace((CharSequence)"\\n", (CharSequence)" ") + " : ";
            for (int i = 0; i < arrstring.length; ++i) {
                string5 = string5 + this.getText(arrstring[i]) + "\n";
            }
            return string5;
        }

        private class Symbol {
            private String letters;
            private String[] lines;

            public Symbol(String string, String[] arrstring) {
                this.letters = "";
                this.lines = new String[5];
                this.lines = this.normalize(arrstring);
                this.letters = string;
            }

            private String[] normalize(String[] arrstring) {
                int n = 0;
                for (int i = 0; i < arrstring.length; ++i) {
                    n = Math.max((int)n, (int)arrstring[i].length());
                }
                for (int j = 0; j < arrstring.length; ++j) {
                    while (arrstring[j].length() < n) {
                        arrstring[j] = arrstring[j] + " ";
                    }
                }
                return arrstring;
            }

            public int getHeight() {
                return this.lines.length;
            }

            public String[] getLines() {
                return this.lines;
            }

            public boolean isIt(char c) {
                for (int i = 0; i < this.letters.length(); ++i) {
                    if (this.letters.charAt(i) != c) continue;
                    return true;
                }
                return false;
            }
        }

    }

    class Rendom
    extends Function {
        Rendom() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        String process(String string, Long l) {
            CommandParser commandParser = new CommandParser(string);
            if (!commandParser.getWord().equals((Object)"\u0440\u0430\u043d\u0434\u043e\u043c")) {
                return super.process(string, l);
            }
            int n = 2000000;
            Random random = new Random();
            int n2 = commandParser.wordsRemaining();
            int n3 = 0;
            switch (n2) {
                case 1: {
                    n = commandParser.getInt();
                    n3 = 0;
                }
                default: {
                    break;
                }
                case 2: {
                    n3 = commandParser.getInt();
                    n = commandParser.getInt();
                }
            }
            int n4 = Math.min((int)n3, (int)n) + random.nextInt(Math.abs((int)(n - n3)));
            return "\u0421\u043b\u0443\u0447\u0430\u0439\u043d\u043e\u0435 \u0447\u0438\u0441\u043b\u043e: " + n4 + ".";
        }
    }

    class Say
    extends Function {
        Say() {
        }

        @Override
        String process(String string, Long l) {
            String[] arrstring = string.split("\\ ");
            if (arrstring.length > 1 && arrstring[0].toLowerCase().equals((Object)"\u0441\u043a\u0430\u0436\u0438")) {
                String string2 = string.replace((CharSequence)"\u0441\u043a\u0430\u0436\u0438 ", (CharSequence)"").replace((CharSequence)"?", (CharSequence)"");
                if (FunctionAnswerer.this.isNegative(string2)) {
                    return "\u042f \u043d\u0435 \u0431\u0443\u0434\u0443 \u044d\u0442\u043e\u0433\u043e \u0433\u043e\u0432\u043e\u0440\u0438\u0442\u044c!";
                }
                return string2;
            }
            return super.process(string, l);
        }
    }

    class Time
    extends Function {
        Time() {
        }

        @Override
        String process(String string, Long l) {
            MessageComparer messageComparer = FunctionAnswerer.this.applicationManager.messageComparer;
            if (messageComparer.isEquals(string, "\u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0447\u0430\u0441") || messageComparer.isEquals(string, "\u0441\u043a\u043e\u043b\u044c\u043a\u043e \u0432\u0440\u0435\u043c\u0435\u043d\u0438") || messageComparer.isEquals(string, "\u043a\u0430\u043a\u043e\u0439 \u0447\u0430\u0441") || messageComparer.isEquals(string, "\u043a\u0430\u043a\u043e\u0435 \u0432\u0440\u0435\u043c\u044f") || messageComparer.isEquals(string, "\u0442\u0435\u043a\u0443\u0449\u0435\u0435 \u0432\u0440\u0435\u043c\u044f") || messageComparer.isEquals(string, "\u0432\u0440\u0435\u043c\u044f") || messageComparer.isEquals(string, "\u0441\u043a\u043e\u043b\u044c\u043a\u043e \u0441\u0435\u0439\u0447\u0430\u0441") || messageComparer.isEquals(string, "\u0442\u043e\u0447\u043d\u043e\u0435 \u0432\u0440\u0435\u043c\u044f")) {
                return "\u041f\u043e \u043c\u043e\u0438\u043c \u0434\u0430\u043d\u043d\u044b\u043c, \u0442\u0435\u043a\u0443\u0449\u0435\u0435 \u0432\u0440\u0435\u043c\u044f: " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ".";
            }
            return super.process(string, l);
        }
    }

    class When
    extends Function {
        When() {
        }

        @Override
        String process(String string, Long l) {
            String[] arrstring = string.split("\\ ");
            String string2 = string.replace((CharSequence)"?", (CharSequence)"").replace((CharSequence)"!", (CharSequence)"");
            if (arrstring.length > 1 && arrstring[0].toLowerCase().equals((Object)"\u043a\u043e\u0433\u0434\u0430")) {
                String string3 = string2.replace((CharSequence)"\u043a\u043e\u0433\u0434\u0430 ", (CharSequence)"");
                Random random = new Random();
                switch (random.nextInt(4)) {
                    default: {
                        return string3;
                    }
                    case 0: {
                        return "\u0427\u0435\u0440\u0435\u0437 " + random.nextInt(35) + " \u0434\u043d\u0435\u0439 " + string3 + ".";
                    }
                    case 1: {
                        return "\u0427\u0435\u0440\u0435\u0437 " + random.nextInt(10) + " \u043c\u0435\u0441\u044f\u0446\u0435\u0432 " + string3 + ".";
                    }
                    case 2: {
                        return "" + (1 + random.nextInt(28)) + "." + (1 + random.nextInt(12)) + "." + (1900 + (random.nextInt(90) + Calendar.getInstance().getTime().getYear())) + " \u0441\u043b\u0443\u0447\u0438\u0442\u0441\u044f " + string3 + ".";
                    }
                    case 3: 
                }
                return "\"" + string3 + "\" \u043d\u0435 \u0441\u043b\u0443\u0447\u0438\u0442\u0441\u044f \u043d\u0438\u043a\u043e\u0433\u0434\u0430.";
            }
            return super.process(string2, l);
        }
    }

    class WhichDayOfMonth
    extends Function {
        WhichDayOfMonth() {
        }

        @Override
        String process(String string, Long l) {
            MessageComparer messageComparer = FunctionAnswerer.this.applicationManager.messageComparer;
            if (messageComparer.isEquals(string, "\u043a\u0430\u043a\u043e\u0435 \u0441\u0435\u0439\u0447\u0430\u0441 \u0447\u0438\u0441\u043b\u043e") || messageComparer.isEquals(string, "\u043a\u0430\u043a\u043e\u0435 \u0441\u0435\u0433\u043e\u0434\u043d\u044f \u0447\u0438\u0441\u043b\u043e") || messageComparer.isEquals(string, "\u0447\u0438\u0441\u043b\u043e")) {
                Calendar calendar = Calendar.getInstance();
                return "\u041f\u043e \u043f\u043e\u0438\u043c \u0434\u0430\u043d\u043d\u044b\u043c, \u0441\u0435\u0439\u0447\u0430\u0441 " + calendar.get(5) + " \u0447\u0438\u0441\u043b\u043e.";
            }
            return super.process(string, l);
        }
    }

    class WhichDayOfWeek
    extends Function {
        WhichDayOfWeek() {
        }

        @Override
        String process(String string, Long l) {
            MessageComparer messageComparer = FunctionAnswerer.this.applicationManager.messageComparer;
            if (messageComparer.isEquals(string, "\u043a\u0430\u043a\u043e\u0439 \u0434\u0435\u043d\u044c") || messageComparer.isEquals(string, "\u043a\u0430\u043a\u043e\u0439 \u0434\u0435\u043d\u044c \u043d\u0435\u0434\u0435\u043b\u0438") || messageComparer.isEquals(string, "\u0434\u0435\u043d\u044c")) {
                Calendar calendar = Calendar.getInstance();
                return "\u041f\u043e \u043f\u043e\u0438\u043c \u0434\u0430\u043d\u043d\u044b\u043c, \u0442\u0435\u043a\u0443\u0449\u0438\u0439 \u0434\u0435\u043d\u044c \u043d\u0435\u0434\u0435\u043b\u0438: " + calendar.get(7) + ".";
            }
            return super.process(string, l);
        }
    }

}

