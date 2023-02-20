package com.imooc.security.core.properties;

public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private ImageCodeProperties sms = new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public ImageCodeProperties getSms() {
        return sms;
    }

    public void setSms(ImageCodeProperties sms) {
        this.sms = sms;
    }
}
