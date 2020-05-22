package CircleTap;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class CircleTap extends PApplet {

double speed = 3;
int radius;
int posx, posy;
int score = 0;

enum State {
  TITLE_SCREEN, 
    IN_GAME, 
    GAME_OVER
}
State gameState = State.TITLE_SCREEN;

int opacity = 0;
int textPosx = -200, textPosy = -200;

public void setup() {
  
  strokeWeight(12);
  textSize(32);
  textFont(loadFont("Fingerpop-48.vlw"));
}


public void draw() {
  background(0xff3BFFFA);
  if (gameState == State.IN_GAME) {
    //fill(100, 255 - radius, 0); //dynamic color?
    fill(255);
    ellipse(posx, posy, radius, radius);  //shooting circle
    radius+= speed;

    fill(255, opacity);
    text("+10", textPosx, textPosy);
    opacity-=6;

    if (radius > 255) {
      gameState = State.GAME_OVER;
    } else {
      fill(0, 0, 0);
      textAlign(LEFT);
      text("Score: " + score, 38, 38);
    }
  } else if (gameState == State.TITLE_SCREEN) {

    fill(255);
    textAlign(CENTER, CENTER);
    text("tap to start", width/2, height/2);
  } else if (gameState == State.GAME_OVER) {

    background(255, 0, 0);
    fill(0, 0, 0);
    textAlign(CENTER, CENTER);
    text("Game Over", width/2, height/2);
    fill(0);
    text("Score: " + score, width/2, height/2 + 40);
    fill(255);
    text("tap to play again", width/2, height/2 + 80);
    radius = 200;
    posx = 5000;
  }
}

public boolean checkShot() {
  //check if mousex and mousey are between the circle
  if (mouseX < posx + radius && mouseX > posx - radius) {
    if (mouseY < posy + radius && mouseY > posy - radius) {
      score += 10;
      return true;
    }
  }
  return false;
}

public void mousePressed() {

  if (gameState == State.IN_GAME) {
    if (checkShot()) {
      radius = 0; 
      pickSpot();
      speed += .2f;
      textPosx = mouseX;
      textPosy = mouseY;
      opacity = 255;
    }
  } else {
    resetGame();
  }
}

public void resetGame() {
  gameState = State.IN_GAME;
  posx = 250;
  posy = 200;
  radius = 1;
  speed = 3;
  score = 0;
}

public void pickSpot() {
  posx = (int)(Math.random() * width);
  posy = (int)(Math.random() * height);
}
  public void settings() {  size(displayWidth, displayHeight); }
}
