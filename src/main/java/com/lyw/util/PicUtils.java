package com.lyw.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

@Slf4j
public class PicUtils {

    /**
     * @param srcImgUrl        源图片网络地址
     * @param tarImgPath       保存的图片路径
     * @param content          文字内容
     */
    public static void addWord(String srcImgUrl, String tarImgPath, String content) {
        try {
            Image whiteImg = ImageIO.read(new File("F:/software/酷Q Pro/data/image/white.png"));
            Image srcImg = ImageIO.read( new URL(srcImgUrl));
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

            int fontSize = srcImgWidth * 9 / 10 / content.length();
            if (fontSize * 5 > srcImgHeight) {
                fontSize = srcImgHeight / 5;
            }
            int whiteBarHeight = (int) (fontSize * 1.2);

            BufferedImage finalImg = new BufferedImage(srcImgWidth, srcImgHeight + whiteBarHeight, bufImg.getType());

            Graphics2D g = finalImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.drawImage(whiteImg, 0, srcImgHeight, srcImgWidth, whiteBarHeight, null);
            g.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
            g.setColor(new Color(0, 0, 0, 255));
            int x = (srcImgWidth - getWatermarkLength(content, g)) / 2;
            int y = (int) Math.round(srcImgHeight + whiteBarHeight - fontSize * 0.2);
            g.drawString(content, x, y);
            g.dispose();

            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(finalImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            log.error("图片添加文字异常", e);
        }
    }

    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

}
