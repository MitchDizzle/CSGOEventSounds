package wtf.dizzle.csgostate.eventsounds;

import java.io.File;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sound {

	@SerializedName("file")
	@Expose
	private File file;

	@SerializedName("volume")
	@Expose
	private Float volume;

	@SerializedName("event")
	@Expose
	private String event;

	public File getFile() {
		return file;
	}

	public void setFile(File soundFile) {
		this.file = soundFile;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setFile(String file) {
		this.file = new File(file);
	}
}
