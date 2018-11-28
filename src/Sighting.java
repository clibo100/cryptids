import java.util.Date;

public class Sighting {

    int CID, viewerID;
    Date date;
    float latitude, longitude;

    public Sighting(int CID, int viewerID, Date date, float latitude, float longitude) {
        this.CID = CID;
        this.viewerID = viewerID;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getViewerID() {
        return viewerID;
    }

    public void setViewerID(int viewerID) {
        this.viewerID = viewerID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
