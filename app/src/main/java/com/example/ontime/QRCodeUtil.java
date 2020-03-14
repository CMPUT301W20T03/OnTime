package com.example.ontime;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;


/**
 * generate QR page by Analysis of the input string
 * and put the qr in to the image view for scanning
 */
class QRCodeUtil {
    /**
     * Create qr code bitmap bitmap.
     *
     * @param content the content
     * @param width   the width
     * @param height  the height
     * @return the bitmap
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(String content, int width, int height){
        return createQRCodeBitmap(content, width, height, "UTF-8", "H", "2", Color.BLACK, Color.WHITE);
    }

    /**
     * Create qr code bitmap bitmap.
     *
     * @param content          the content
     * @param width            the width
     * @param height           the height
     * @param character_set    the character set
     * @param error_correction the error correction
     * @param margin           the margin
     * @param color_black      the color black
     * @param color_white      the color white
     * @return the bitmap
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            @Nullable String character_set, @Nullable String error_correction, @Nullable String margin,
                                            @ColorInt int color_black, @ColorInt int color_white){


        if(TextUtils.isEmpty(content)){ /**Null string contents**/
            return null;
        }

        if(width < 0 || height < 0){
            return null;
        }

        try { /**Set the relevant configuration of qr code to generate BitMatrix(BitMatrix) object**/

            Hashtable<EncodeHintType, String> hints = new Hashtable<>();

            if(!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set); //Character transcoding format setting
            }

            if(!TextUtils.isEmpty(error_correction)){
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction);//Fault tolerance level Settings
            }

            if(!TextUtils.isEmpty(margin)){
                hints.put(EncodeHintType.MARGIN, margin);/**Blank margin setting**/
            }
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            /**Creates an array of pixels and assigns a color value to the array elements based on the BitMatrix object**/
            int[] pixels = new int[width * height];
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    if(bitMatrix.get(x, y)){
                        pixels[y * width + x] = color_black;
                    } else {
                        pixels[y * width + x] = color_white;
                    }
                }
            }
            /**
             * Create a Bitmap object, set the color value of each pixel of
             * the Bitmap according to the pixel array, and then return the Bitmap object
            **/
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }
}
