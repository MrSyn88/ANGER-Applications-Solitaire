/**
 * 
 */
package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SolitaireSettings {

	private DoubleProperty masterVolume = new SimpleDoubleProperty();
	private DoubleProperty musicVolume = new SimpleDoubleProperty();
	private DoubleProperty effectsVolume = new SimpleDoubleProperty();
	private BooleanProperty undoable = new SimpleBooleanProperty();
	private BooleanProperty showTimes = new SimpleBooleanProperty();
	private BooleanProperty solveable = new SimpleBooleanProperty();
	private IntegerProperty selectedBackIndex = new SimpleIntegerProperty();
	private File selectedCardBack;
	private File cardBackLocation = new File("src/application/view/Backs");
	private Map<String, File> cardBacks;
	private File settingsFile;

	/**
	 * 
	 */
	public SolitaireSettings() {
		super();

		this.settingsFile = new File("settings.txt");
		File[] fileList = cardBackLocation.listFiles();
		cardBacks = new HashMap<String, File>();
		for (File cardBack : fileList) {
			cardBacks.put(cardBack.getName(), cardBack);
		}

		if (this.settingsFile.exists()) {
			try {
				this.loadSettings();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.setDefaults();
		}

	}

	/**
	 * @param settingsFile
	 * @throws IOException
	 */
	public SolitaireSettings(File settingsFile) throws IOException {
		super();
		this.settingsFile = settingsFile;
		File[] fileList = cardBackLocation.listFiles();
		cardBacks = new HashMap<String, File>();
		for (File cardBack : fileList) {
			cardBacks.put(cardBack.getName(), cardBack);
		}

		if (this.settingsFile.exists()) {
			this.loadSettings();
		} else {
			this.setDefaults();
		}
	}

	private void setDefaults() {
		setMasterVolume(50.0);
		setMusicVolume(50.0);
		setEffectsVolume(50.0);
		setUndoable(true);
		setSolveable(true);
		setShowTimes(true);
		if (!cardBacks.isEmpty()) {
			for (String key : cardBacks.keySet()) {
				setSelectedCardBack(key);
				break;
			}
		}
		setSelectedBackIndex(0);

	}

	/**
	 * @return
	 */
	public DoubleProperty masterVolumeProperty() {
		return masterVolume;
	}

	/**
	 * @return
	 */
	public DoubleProperty musicVolumeProperty() {
		return musicVolume;
	}

	/**
	 * @return
	 */
	public DoubleProperty effectsVolumeProperty() {
		return effectsVolume;
	}

	public BooleanProperty undoableProperty() {
		return undoable;
	}

	public BooleanProperty showTimesProperty() {
		return showTimes;
	}

	public BooleanProperty solveableProperty() {
		return solveable;
	}

	/**
	 * @return
	 */
	public Double getMasterVolume() {
		return masterVolume.getValue();
	}

	/**
	 * @param masterVolume
	 */
	public void setMasterVolume(Double masterVolume) {
		this.masterVolume.setValue(masterVolume);
	}

	/**
	 * @return the musicVolume
	 */
	public Double getMusicVolume() {
		return musicVolume.getValue();
	}

	/**
	 * @param musicVolume the musicVolume to set
	 */
	public void setMusicVolume(Double musicVolume) {
		this.musicVolume.setValue(musicVolume);
	}

	/**
	 * @return the effectsVolume
	 */
	public Double getEffectsVolume() {
		return effectsVolume.getValue();
	}

	/**
	 * @param effectsVolume the effectsVolume to set
	 */
	public void setEffectsVolume(Double effectsVolume) {
		this.effectsVolume.setValue(effectsVolume);
	}

	/**
	 * @return the undoable
	 */
	public Boolean getUndoable() {
		return undoable.getValue();
	}

	/**
	 * @param undoable the undoable to set
	 */
	public void setUndoable(Boolean undoable) {
		this.undoable.setValue(undoable);
	}

	/**
	 * @return the showTimes
	 */
	public Boolean getShowTimes() {
		return showTimes.getValue();
	}

	/**
	 * @param showTimes the showTimes to set
	 */
	public void setShowTimes(Boolean showTimes) {
		this.showTimes.setValue(showTimes);
	}

	/**
	 * @return the solveable
	 */
	public Boolean getSolveable() {
		return solveable.getValue();
	}

	/**
	 * @param solveable the solveable to set
	 */
	public void setSolveable(Boolean solveable) {
		this.solveable.setValue(solveable);
	}

	/**
	 * @return the selectedBackIndex
	 */
	public IntegerProperty getSelectedBackIndexProperty() {
		return selectedBackIndex;
	}

	/**
	 * @param selectedBackIndex the selectedBackIndex to set
	 */
	public void setSelectedBackIndex(Integer selectedBackIndex) {
		this.selectedBackIndex.setValue(selectedBackIndex);
	}

	/**
	 * @return
	 */
	public Integer getSelectedBackIndex() {
		return this.selectedBackIndex.getValue();
	}

	/**
	 * @return
	 */
	public File getSelectedCardBack() {
		return selectedCardBack;
	}

	/**
	 * @param selectedCard
	 */
	public void setSelectedCardBack(String selectedCard) {
		this.selectedCardBack = this.cardBacks.get(selectedCard);
	}

	/**
	 * @return the cardBacks
	 */
	public Map<String, File> getCardBacks() {
		return cardBacks;
	}

	/**
	 * @return the settingsFile
	 */
	public File getSettingsFile() {
		return settingsFile;
	}

	/**
	 * @param settingsFile the settingsFile to set
	 */
	public void setSettingsFile(File settingsFile) {
		this.settingsFile = settingsFile;
	}

	public void saveSettings(File settingsFile) throws IOException {
		// Open the settings file
		PrintWriter outFile = new PrintWriter(settingsFile);

		// Write out all the settings to a file
		outFile.println(getMasterVolume());
		outFile.println(getMusicVolume());
		outFile.println(getEffectsVolume());
		outFile.println(getUndoable());
		outFile.println(getSolveable());
		outFile.println(getShowTimes());
		outFile.println(getSelectedCardBack());
		outFile.println(getSelectedBackIndex());

		outFile.close();
	}

	public void loadSettings(File settingsFile) throws IOException {
		Scanner inScan = new Scanner(settingsFile);
		if (inScan.hasNextDouble())
			setMasterVolume(inScan.nextDouble());
		else
			setMasterVolume(50.0);
		if (inScan.hasNextDouble())
			setMusicVolume(inScan.nextDouble());
		else
			setMusicVolume(50.0);
		if (inScan.hasNextDouble())
			setEffectsVolume(inScan.nextDouble());
		else
			setEffectsVolume(50.0);
		if (inScan.hasNextDouble())
			setUndoable(inScan.nextBoolean());
		else
			setUndoable(true);
		if (inScan.hasNextDouble())
			setSolveable(inScan.nextBoolean());
		else
			setSolveable(true);
		if (inScan.hasNextDouble())
			setShowTimes(inScan.nextBoolean());
		else
			setShowTimes(true);
		if (inScan.hasNext())
			setSelectedCardBack(inScan.next());
		else
			setSelectedCardBack("");
		if(inScan.hasNextInt())
			setSelectedBackIndex(inScan.nextInt());
		else
			setSelectedBackIndex(0);

		inScan.close();
	}

	public void saveSettings() throws IOException {
		this.saveSettings(this.settingsFile);
	}

	public void loadSettings() throws IOException {
		this.loadSettings(this.settingsFile);
	}

}
