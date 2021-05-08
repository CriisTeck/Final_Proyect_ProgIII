package persistence;

import models.User;
import structures.treeavl.Tree;
import utils.JSONUtils;
import utils.StringConstants;

import java.io.*;

import static utils.EncrypterString.*;

public class JSONUserInfo {
    private BufferedReader bufferedReader;
    private File file;

    public JSONUserInfo() {
        try {
            file = new File(desencrypt(StringConstants.PATH_FILE_USERS));
            if (!file.exists()) file.createNewFile();
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


    public void writeJSONFile(Tree<User> data) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(encrypt(JSONUtils.objectToJSON(data, Tree.class)));
        bufferedWriter.close();
    }

    public Tree readJSONFile() throws IOException {
        StringBuilder total = new StringBuilder();
        String line = "";
        while (line != null) {
            total.append(line);
            line = bufferedReader.readLine();
        }
        line = desencrypt(total.toString());
        return JSONUtils.readDataFromJson(line);
    }
}
