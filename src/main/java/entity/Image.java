package entity;

import java.io.FileInputStream;

public class Image extends BaseEntity {
    private FileInputStream imageFile;

    public Image() {
    }

    public FileInputStream getImageFile() {
        return imageFile;
    }

    public void setImageFile(FileInputStream imageFile) {
        if (imageFile == null) {
            throw new NullPointerException("Attempt to set null to imageFile");
        }
        this.imageFile = imageFile;
    }
}
