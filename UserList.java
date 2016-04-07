  java.io.File
 *  java.io.FileReader
 *  java.io.FileWriter
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.AbstractMap
 *  java.util.AbstractMap$SimpleEntry
 *  java.util.ArrayList
 *  java.util.Map
 *  java.util.Map$Entry
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.fsoft.vktest.common;

import com.fsoft.vktest.ApplicationManager;
import com.fsoft.vktest.VkCommunicator;
import com.fsoft.vktest.common.Command;
import com.fsoft.vktest.common.CommandParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserList
implements Command {
    ApplicationManager applicationManager = null;
    File file;
    ArrayList<Map.Entry<Long, String>> list = new ArrayList();
    public String name;
    final Object sync = new Object();

    public UserList(String string, ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
        this.name = string;
        this.file = new File(ApplicationManager.getHomeFolder() + File.separator + string);
    }

    private Map.Entry<Long, String> getIfExists(Long l) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (!((Long)((Map.Entry)this.list.get(i)).getKey()).equals((Object)l)) continue;
            return (Map.Entry)this.list.get(i);
        }
        return null;
    }

    private String getParcelable() {
        this.log(". (" + this.name + ") \u0421\u043e\u0437\u0434\u0430\u043d\u0438\u0435 \u0441\u0442\u0440\u043e\u043a\u0438 \u0434\u043b\u044f \u0437\u0430\u043f\u0438\u0441\u0438...");
        JSONArray jSONArray = new JSONArray();
        int n = 0;
        do {
            if (n >= this.list.size()) break;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", ((Map.Entry)this.list.get(n)).getKey());
            jSONObject.put("comment", ((Map.Entry)this.list.get(n)).getValue());
            jSONArray.put((Object)jSONObject);
            ++n;
        } while (true);
        try {
            String string = jSONArray.toString();
            this.log(". (" + this.name + ")\u0421\u0442\u0440\u043e\u043a\u0430 \u0434\u043b\u044f \u0437\u0430\u043f\u0438\u0441\u0438 \u0441\u043e\u0437\u0434\u0430\u043d\u0430: " + string);
            return string;
        }
        catch (Exception var4_5) {
            this.log("! (" + this.name + ")\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u043e\u0437\u0434\u0430\u043d\u0438\u044f \u0441\u0442\u0440\u043e\u043a\u0438 \u0434\u043b\u044f \u0437\u0430\u043f\u0438\u0441\u0438: " + var4_5.toString());
            return "";
        }
    }

    private String log(String string) {
        ApplicationManager.log(string);
        return string;
    }

    private void setParcelable(String string) {
        this.log(". (" + this.name + ") \u0420\u0430\u0437\u0431\u043e\u0440 \u0441\u0442\u0440\u043e\u043a\u0438:" + string);
        if (!(string == null || string.equals((Object)""))) {
            if (string.contains((CharSequence)"|")) {
                String[] arrstring = string.split("\\|");
                for (int i = 0; i < arrstring.length; ++i) {
                    this.add(arrstring[i], "\u043d\u0435\u0442 \u043e\u043f\u0438\u0441\u0430\u043d\u0438\u044f");
                }
                this.log(". (" + this.name + ") \u0421\u0442\u0440\u043e\u043a\u0430 \u0440\u0430\u0437\u043e\u0431\u0440\u0430\u043d\u0430. \u041f\u043e\u043b\u0443\u0447\u0435\u043d\u043e " + this.list.size() + " \u0441\u0442\u0440\u0430\u043d\u0438\u0446.");
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            int n = 0;
            do {
                if (n >= jSONArray.length()) break;
                JSONObject jSONObject = jSONArray.getJSONObject(n);
                this.add(jSONObject.getLong("id"), jSONObject.getString("comment"));
                ++n;
            } while (true);
            try {
                this.log(". (" + this.name + ") \u0421\u0442\u0440\u043e\u043a\u0430 \u0440\u0430\u0437\u043e\u0431\u0440\u0430\u043d\u0430. \u041f\u043e\u043b\u0443\u0447\u0435\u043d\u043e " + this.list.size() + " \u0441\u0442\u0440\u0430\u043d\u0438\u0446.");
                return;
            }
            catch (Exception var6_7) {
                var6_7.printStackTrace();
                this.log("! \u041e\u0448\u0438\u0431\u043a\u0430 \u0440\u0430\u0437\u0431\u043e\u0440\u0430 \u0441\u0442\u0440\u043e\u043a\u0438 " + this.name + " : " + var6_7.toString());
                return;
            }
        }
        this.log(". (" + this.name + ") \u0421\u0442\u0440\u043e\u043a\u0430 \u043f\u0443\u0441\u0442\u0430. \u0421\u043f\u0438\u0441\u043e\u043a \u043f\u0443\u0441\u0442.");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public String add(long var1_1, String var3_2) {
        this.log(". (" + this.name + ") \u0412\u043d\u0435\u0441\u0435\u043d\u0438\u0435 \u0432 \u0441\u043f\u0438\u0441\u043e\u043a \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " ...");
        var13_4 = var5_3 = this.sync;
        // MONITORENTER : var13_4
        ** if (var1_1 != -1 && var1_1 != 0) goto lbl9
lbl5: // 1 sources:
        try {
            var9_5 = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " \u0432 \u0441\u043f\u0438\u0441\u043e\u043a " + this.name + ". \u0412\u043e\u0437\u043c\u043e\u0436\u043d\u043e, \u0432\u044b \u0432\u0432\u0435\u043b\u0438 \u043d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439 ID \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b.";
            // MONITOREXIT : var13_4
            return var9_5;
lbl9: // 1 sources:
            if (this.getIfExists(var1_1) != null) {
                var12_6 = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " \u0432 \u0441\u043f\u0438\u0441\u043e\u043a " + this.name + ". \u0421\u0442\u0440\u0430\u043d\u0438\u0446\u0430 \u0443\u0436\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u044d\u0442\u043e\u043c \u0441\u043f\u0438\u0441\u043a\u0435.";
                // MONITOREXIT : var13_4
                return var12_6;
            }
            if (this.list.add((Object)new AbstractMap.SimpleEntry((Object)var1_1, (Object)var3_2))) {
                var11_7 = "\u0421\u0442\u0440\u0430\u043d\u0438\u0446\u0430 " + var1_1 + " \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0430 \u0432 \u0441\u043f\u0438\u0441\u043e\u043a " + this.name + " \u0441 \u043a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0435\u043c " + var3_2 + ". \u0421\u0435\u0439\u0447\u0430\u0441 \u0432 \u044d\u0442\u043e\u043c \u0441\u043f\u0438\u0441\u043a\u0435 " + this.list.size() + " \u0441\u0442\u0440\u0430\u043d\u0438\u0446.";
                // MONITOREXIT : var13_4
                return var11_7;
            }
            var10_8 = "\u0421\u0442\u0440\u0430\u043d\u0438\u0446\u0430 " + var1_1 + " \u043f\u043e\u0447\u0435\u043c\u0443-\u0442\u043e \u043d\u0435 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0430 \u0432 \u0441\u043f\u0438\u0441\u043e\u043a " + this.name + ". \u0421\u0435\u0439\u0447\u0430\u0441 \u0432 \u044d\u0442\u043e\u043c \u0441\u043f\u0438\u0441\u043a\u0435 " + this.list.size() + " \u0441\u0442\u0440\u0430\u043d\u0438\u0446.";
            // MONITOREXIT : var13_4
            return var10_8;
        }
        catch (Exception var6_9) {
            var6_9.printStackTrace();
            var7_10 = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " \u0432 " + this.name + ". " + var6_9.toString();
            // MONITOREXIT : var13_4
            return var7_10;
        }
    }

    public String add(String string, String string2) {
        try {
            String string3 = this.add(this.applicationManager.getUserID(string), string2);
            return string3;
        }
        catch (Exception var3_4) {
            var3_4.printStackTrace();
            return "\u041e\u0448\u0438\u0431\u043a\u0430 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + string + " \u0432 " + this.name + ". " + var3_4.toString();
        }
    }

    public String clr() {
        this.list.clear();
        return ". \u0421\u043f\u0438\u0441\u043e\u043a " + this.name + " \u043e\u0447\u0438\u0449\u0435\u043d. \u0420\u0430\u0437\u043c\u0435\u0440 \u0441\u043f\u0438\u0441\u043a\u0430 \u0441\u0435\u0439\u0447\u0430\u0441: " + this.list.size() + " \u044d\u043b\u0435\u043c\u0435\u043d\u0442\u043e\u0432.\n";
    }

    public boolean contains(long l) {
        if (this.getIfExists(l) != null) {
            return true;
        }
        return false;
    }

    public boolean contains(String string) {
        try {
            boolean bl = this.contains(this.applicationManager.getUserID(string));
            return bl;
        }
        catch (Exception var2_3) {
            return false;
        }
    }

    public long get(int n) {
        return (Long)((Map.Entry)this.list.get(n)).getKey();
    }

    /*
     * Enabled aggressive block sorting
     */
    public String get() {
        String string = "\u0421\u043f\u0438\u0441\u043e\u043a " + this.name + "  (" + this.list.size() + "): \n";
        for (int i = 0; i < this.list.size(); ++i) {
            long l = (Long)((Map.Entry)this.list.get(i)).getKey();
            String string2 = (String)((Map.Entry)this.list.get(i)).getValue();
            string = l >= 0 ? string + "http://vk.com/id" + l + "  (" + this.applicationManager.vkCommunicator.getUserName(l) + ", " + string2 + ")\n" : string + "http://vk.com/club" + Math.abs((long)l) + "  (" + this.applicationManager.vkCommunicator.getUserName(l) + ", " + string2 + ") \n";
        }
        return string;
    }

    public String getComment(int n) {
        return (String)((Map.Entry)this.list.get(n)).getValue();
    }

    @Override
    public String getHelp() {
        return "[ \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u0443 \u0432 \u0441\u043f\u0438\u0441\u043e\u043a " + this.name + " ] \n" + "---| " + ApplicationManager.botcmd + " " + this.name + " add  <\u0441\u0441\u044b\u043b\u043a\u0430 \u0444\u043e\u0440\u043c\u0430\u0442\u0430 https://vk.com/ihabotclub> <\u043a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439>\n\n" + "[ \u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u0443 \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + " ] \n" + "---| " + ApplicationManager.botcmd + " " + this.name + " rem  <\u0441\u0441\u044b\u043b\u043a\u0430 \u0444\u043e\u0440\u043c\u0430\u0442\u0430 https://vk.com/ihabotclub> \n\n" + "[ \u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a " + this.name + " ] \n" + "---| " + ApplicationManager.botcmd + " " + this.name + " clr \n\n" + "[ \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u043d\u0438\u0435 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + " ] \n" + "---| " + ApplicationManager.botcmd + " " + this.name + " get \n\n";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void load() {
        this.log(". \u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430 " + this.name + " \u0438\u0437 \u0444\u0430\u0439\u043b\u0430 " + this.file.getPath() + " ...");
        if (!this.file.isFile()) {
            this.log(". (" + this.name + ") \u0444\u0430\u0439\u043b\u0430 \u043d\u0435\u0442: " + this.file.getPath() + ".");
            return;
        }
        try {
            FileReader fileReader = new FileReader(this.file);
            StringBuilder stringBuilder = new StringBuilder();
            while (fileReader.ready()) {
                stringBuilder.append((char)fileReader.read());
            }
            fileReader.close();
            String string = stringBuilder.toString();
            this.log(". \u041f\u0440\u043e\u0447\u0438\u0442\u0430\u043d\u043e: " + string);
            this.setParcelable(string);
            return;
        }
        catch (Exception var5_3) {
            var5_3.printStackTrace();
            this.log("! \u041e\u0448\u0438\u0431\u043a\u0430 \u0447\u0442\u0435\u043d\u0438\u044f \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u0430 " + this.file.getPath() + " : " + var5_3.toString());
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public String process(String var1_1) {
        var2_2 = new CommandParser(var1_1);
        if (!var2_2.getWord().equals((Object)this.name)) {
            return "";
        }
        var3_3 = var2_2.getWord();
        var4_4 = -1;
        switch (var3_3.hashCode()) {
            case 96417: {
                if (var3_3.equals((Object)"add")) {
                    var4_4 = 0;
                    ** break;
                } else {
                    ** GOTO lbl18
                }
            }
            case 112794: {
                if (var3_3.equals((Object)"rem")) {
                    var4_4 = 1;
                    ** break;
                } else {
                    ** GOTO lbl18
                }
            }
            case 102230: {
                if (var3_3.equals((Object)"get")) {
                    var4_4 = 2;
                }
            }
lbl18: // 10 sources:
            default: {
                ** GOTO lbl23
            }
            case 98601: 
        }
        if (var3_3.equals((Object)"clr")) {
            var4_4 = 3;
        }
lbl23: // 4 sources:
        switch (var4_4) {
            default: {
                return "";
            }
            case 0: {
                return this.add(var2_2.getWord(), var2_2.getText());
            }
            case 1: {
                return this.rem(var2_2.getWord());
            }
            case 2: {
                return this.get();
            }
            case 3: 
        }
        return this.clr();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public String rem(long var1_1) {
        var11_3 = var3_2 = this.sync;
        // MONITORENTER : var11_3
        ** if (var1_1 != -1) goto lbl8
lbl4: // 1 sources:
        try {
            var10_4 = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + ". \u0412\u043e\u0437\u043c\u043e\u0436\u043d\u043e, \u0432\u044b \u0432\u0432\u0435\u043b\u0438 \u043d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439 ID \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b.";
            // MONITOREXIT : var11_3
            return var10_4;
lbl8: // 1 sources:
            if (this.getIfExists(var1_1) == null) {
                var9_5 = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + ". \u0421\u0442\u0440\u0430\u043d\u0438\u0446\u0430 \u043d\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u044d\u0442\u043e\u043c \u0441\u043f\u0438\u0441\u043a\u0435.";
                // MONITOREXIT : var11_3
                return var9_5;
            }
            if (this.list.remove(this.getIfExists(var1_1))) {
                var8_6 = "\u0421\u0442\u0440\u0430\u043d\u0438\u0446\u0430 " + var1_1 + " \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0435\u043d\u0430 \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + ". \u0421\u0435\u0439\u0447\u0430\u0441 \u0432 \u044d\u0442\u043e\u043c \u0441\u043f\u0438\u0441\u043a\u0435 " + this.list.size() + " \u0441\u0442\u0440\u0430\u043d\u0438\u0446.";
                // MONITOREXIT : var11_3
                return var8_6;
            }
            var7_7 = "\u0421\u0442\u0440\u0430\u043d\u0438\u0446\u0430 " + var1_1 + " \u043f\u043e\u0447\u0435\u043c\u0443-\u0442\u043e \u043d\u0435 \u0443\u0434\u0430\u043b\u0435\u043d\u0430 \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + ". \u0421\u0435\u0439\u0447\u0430\u0441 \u0432 \u044d\u0442\u043e\u043c \u0441\u043f\u0438\u0441\u043a\u0435 " + this.list.size() + " \u0441\u0442\u0440\u0430\u043d\u0438\u0446.";
            // MONITOREXIT : var11_3
            return var7_7;
        }
        catch (Exception var4_8) {
            var4_8.printStackTrace();
            var5_9 = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + var1_1 + " \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 " + this.name + ". " + var4_8.toString();
            // MONITOREXIT : var11_3
            return var5_9;
        }
    }

    public String rem(String string) {
        try {
            String string2 = this.rem(this.applicationManager.getUserID(string));
            return string2;
        }
        catch (Exception var2_3) {
            var2_3.printStackTrace();
            return "\u041e\u0448\u0438\u0431\u043a\u0430 \u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b " + string + " \u0432 " + this.name + ". " + var2_3.toString();
        }
    }

    public String save() {
        String string = "" + this.log(new StringBuilder().append(". (").append(this.name).append(")\u0417\u0430\u043f\u0438\u0441\u044c \u0432 \u0444\u0430\u0439\u043b ").append(this.file.getPath()).append(" ...\n").toString());
        try {
            File file = this.file.getParentFile();
            if (!file.exists()) {
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(this.file);
            String string2 = this.getParcelable();
            string = string + this.log(new StringBuilder().append(". (").append(this.name).append(")\u0417\u0430\u043f\u0438\u0441\u044c: ").append(string2).append("\n").toString());
            fileWriter.write(string2);
            fileWriter.close();
            String string3 = string + this.log(new StringBuilder().append(". (").append(this.name).append(")\u0417\u0430\u043f\u0438\u0441\u0430\u043d\u043e.\n").toString());
            return string3;
        }
        catch (Exception var2_6) {
            var2_6.printStackTrace();
            return string + this.log(new StringBuilder().append("! (").append(this.name).append(")\u041e\u0448\u0438\u0431\u043a\u0430 \u0437\u0430\u043f\u0438\u0441\u0438 ").append(this.file.getPath()).append(" : ").append(var2_6.toString()).append("\n").toString());
        }
    }

    public int size() {
        return this.list.size();
    }
}

