package com.wx.mp.utils;

import com.wx.mp.entity.Ana;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author R
 * @date 2022/8/28 - 15:33
 */
public class FileUtil {

    /**
     * 按行读取全部文件数据
     *
     * @param strFile
     */
    public static List<Ana> readAna(String strFile) throws IOException {
        InputStreamReader inStrR = new InputStreamReader(new FileInputStream(strFile), "UTF-8");
        // character streams
        BufferedReader br = new BufferedReader(inStrR);
        List<Ana> anaList = new ArrayList<>();
        String line = br.readLine();
        List<String> englishList = new ArrayList<>();
        List<String> chineseList = new ArrayList<>();
        int i = 1;
        while (line != null) {
//            strSb.append(line).append("\r\n");
            if (i % 2 == 1) {
                englishList.add(line.trim());
            } else {
                chineseList.add(line.trim());
            }
            i++;
            line = br.readLine();
        }
        if (chineseList.size() == englishList.size()) {
            for (int j = 0; j < chineseList.size(); j++) {
                Ana ana = new Ana();
                ana.setChinese(chineseList.get(j))
                        .setEnglish(englishList.get(j));
                anaList.add(ana);
            }
        }
        return anaList;
    }

    public static List<String> readColor(String strFile) throws IOException {
        InputStreamReader inStrR = new InputStreamReader(new FileInputStream(strFile), "UTF-8");
        // character streams
        BufferedReader br = new BufferedReader(inStrR);
        String line = br.readLine();
        List<String> colorList = new ArrayList<>();
        while (line != null) {
//            strSb.append(line).append("\r\n");
            int i = line.trim().indexOf("-");
            if (i <= 0) {
                colorList.add(line.trim());
            }
            line = br.readLine();
        }
        return colorList;
    }

    public static Map<String, String> readCity(String strFile) throws IOException {
        InputStreamReader inStrR = new InputStreamReader(new FileInputStream(strFile), "UTF-8");
        // character streams
        BufferedReader br = new BufferedReader(inStrR);
        String line = br.readLine();
        Map<String, String> cityMap = new HashMap<>();
        while (line != null) {
            String[] str = line.split(":");
            cityMap.put(str[0], str[1]);
            line = br.readLine();
        }
        return cityMap;
    }
}
