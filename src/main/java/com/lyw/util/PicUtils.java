package com.lyw.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

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

    public static BufferedImage changeSize(BufferedImage bufferedImage, int width, int height) {
        // 缩放比例
        double ratio;
        // 计算缩放比例
        if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
            ratio = (new Integer(height)).doubleValue() / bufferedImage.getHeight();
        } else {
            ratio = (new Integer(width)).doubleValue() / bufferedImage.getWidth();
        }
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        return op.filter(bufferedImage, null);
    }

    public static void mergeImage(List<String> images, File descImage) {
        if (images == null || images.isEmpty()) {
            return;
        }
        try {
            int width, height;
            if (images.size() <= 5) {
                width = 80 * images.size();
                height = 80;
            } else {
                width = 80 * 5;
                height = 80 * ((images.size() - 1) / 5 + 1);
            }
            BufferedImage descBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics2d = (Graphics2D) descBufferedImage.getGraphics();
            graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            for (int i = 0; i < images.size(); ++i) {
                BufferedImage bufferedImage = ImageIO.read(new File(images.get(i)));
                bufferedImage = changeSize(bufferedImage, 80, 80);
                // 往画布上添加图片,并设置边距
                int x = 80 * (i % 5);
                int y = 80 * (i / 5);
                graphics2d.drawImage(bufferedImage, null, x, y);
            }
            graphics2d.dispose();
            // 输出新图片
            ImageIO.write(descBufferedImage, "png", descImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
