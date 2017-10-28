package models;

/**
 * Created by Prem on 28-10-2017.
 */

class Step {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;


    public Step(int i, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.i = i;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }


    public Step(){

    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
