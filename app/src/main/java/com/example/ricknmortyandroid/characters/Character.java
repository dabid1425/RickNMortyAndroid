package com.example.ricknmortyandroid.characters;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Character implements Parcelable {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private Origin origin;
    private SingleLocation location;
    private String image;
    private List<String> episode;
    private String url;
    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public SingleLocation getLocation() {
        return location;
    }

    public void setLocation(SingleLocation location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

        protected Character(Parcel in) {
            id = in.readInt();
            name = in.readString();
            status = in.readString();
            species = in.readString();
            type = in.readString();
            gender = in.readString();
            origin = in.readParcelable(Origin.class.getClassLoader());
            location = in.readParcelable(SingleLocation.class.getClassLoader());
            image = in.readString();
            episode = in.createStringArrayList();
            url = in.readString();
            created = in.readString();
        }

        public static final Creator<Character> CREATOR = new Creator<Character>() {
            @Override
            public Character createFromParcel(Parcel in) {
                return new Character(in);
            }

            @Override
            public Character[] newArray(int size) {
                return new Character[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(status);
            dest.writeString(species);
            dest.writeString(type);
            dest.writeString(gender);
            dest.writeParcelable(origin, flags);
            dest.writeParcelable(location, flags);
            dest.writeString(image);
            dest.writeStringList(episode);
            dest.writeString(url);
            dest.writeString(created);
        }

        @Override
        public int describeContents() {
            return 0;
        }
}
