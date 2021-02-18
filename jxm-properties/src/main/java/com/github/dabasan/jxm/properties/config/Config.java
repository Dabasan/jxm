package com.github.dabasan.jxm.properties.config;

/**
 * Config
 * 
 * @author Daba
 *
 */
public class Config {
	// Key code
	public KeyCode turnUp;
	public KeyCode turnDown;
	public KeyCode turnLeft;
	public KeyCode turnRight;
	public KeyCode moveForward;
	public KeyCode moveBackward;
	public KeyCode moveLeft;
	public KeyCode moveRight;
	public KeyCode walk;
	public KeyCode jump;
	public KeyCode reload;
	public KeyCode dropWeapon;
	public KeyCode zoom;
	public KeyCode fireMode;
	public KeyCode switchWeapon;
	public KeyCode weapon1;
	public KeyCode weapon2;
	public KeyCode fire;
	// Other config
	public int mouseSensitivity;
	public int brightness;
	public WindowMode windowMode;
	public boolean enableSound;
	public boolean enableBlood;
	public boolean invertMouse;
	public boolean frameSkip;
	public boolean anotherGunsight;
	public String name;

	public Config() {
		this.reset();
	}
	public void reset() {
		// Key code
		turnUp = KeyCode.KEY_UP;
		turnDown = KeyCode.KEY_DOWN;
		turnLeft = KeyCode.KEY_LEFT;
		turnRight = KeyCode.KEY_RIGHT;
		moveForward = KeyCode.KEY_W;
		moveBackward = KeyCode.KEY_S;
		moveLeft = KeyCode.KEY_A;
		moveRight = KeyCode.KEY_D;
		walk = KeyCode.KEY_TAB;
		jump = KeyCode.KEY_SPACE;
		reload = KeyCode.KEY_R;
		dropWeapon = KeyCode.KEY_G;
		zoom = KeyCode.KEY_SHIFT;
		fireMode = KeyCode.KEY_X;
		switchWeapon = KeyCode.KEY_Z;
		weapon1 = KeyCode.KEY_1;
		weapon2 = KeyCode.KEY_2;
		fire = KeyCode.KEY_MOUSE_L;
		// Other config
		mouseSensitivity = 25;
		brightness = 4;
		windowMode = WindowMode.FULL_SCREEN;
		enableSound = true;
		enableBlood = true;
		invertMouse = false;
		frameSkip = false;
		anotherGunsight = false;
		name = "xopsplayer";
	}

	@Override
	public String toString() {
		return "Config [turnUp=" + turnUp + ", turnDown=" + turnDown + ", turnLeft=" + turnLeft
				+ ", turnRight=" + turnRight + ", moveForward=" + moveForward + ", moveBackward="
				+ moveBackward + ", moveLeft=" + moveLeft + ", moveRight=" + moveRight + ", walk="
				+ walk + ", jump=" + jump + ", reload=" + reload + ", dropWeapon=" + dropWeapon
				+ ", zoom=" + zoom + ", fireMode=" + fireMode + ", switchWeapon=" + switchWeapon
				+ ", weapon1=" + weapon1 + ", weapon2=" + weapon2 + ", fire=" + fire
				+ ", mouseSensitivity=" + mouseSensitivity + ", brightness=" + brightness
				+ ", windowMode=" + windowMode + ", enableSound=" + enableSound + ", enableBlood="
				+ enableBlood + ", invertMouse=" + invertMouse + ", frameSkip=" + frameSkip
				+ ", anotherGunsight=" + anotherGunsight + ", name=" + name + "]";
	}
}
