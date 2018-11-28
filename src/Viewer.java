public class Viewer {

    String name, location, credentials;
    int age, publications, sightings;

    public Viewer(String name, String location, String credentials, int age, int publications, int sightings) {
        this.name = name;
        this.location = location;
        this.credentials = credentials;
        this.age = age;
        this.publications = publications;
        this.sightings = sightings;
    }

    public Viewer() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPublications() {
        return publications;
    }

    public void setPublications(int publications) {
        this.publications = publications;
    }

    public int getSightings() {
        return sightings;
    }

    public void setSightings(int sightings) {
        this.sightings = sightings;
    }
}
