import java.util.Date;

@SuppressWarnings("unused")
public class Sighting {

    private int CID, viewerID;
    private Date date;
    private float latitude, longitude;

    Sighting(int CID, int viewerID, Date date, float latitude, float longitude) {
        this.CID = CID;
        this.viewerID = viewerID;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    int getViewerID() {
        return viewerID;
    }

    public void setViewerID(int viewerID) {
        this.viewerID = viewerID;
    }

    Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
