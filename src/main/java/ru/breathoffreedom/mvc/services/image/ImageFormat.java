package ru.breathoffreedom.mvc.services.image;

/**
 * Created by boris_azanov on 28.11.16.
 */
public enum ImageFormat {
    original,
    postMainImage,
    postContentImage;

    public static ImageFormat[] getFormatsByDir(ImageDir dir) {
        if (ImageDir.postContent == dir) {
            return new ImageFormat[]{ postContentImage };
        } else if (ImageDir.postMain == dir) {
            return new ImageFormat[]{ postMainImage };
        }
        return new ImageFormat[]{};
    }
}
