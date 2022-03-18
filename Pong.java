import processing.core.PApplet;

public class Pong extends PApplet {

    boolean upPressed = false;
    boolean downPressed = false;

    int width = 700;
    int height = 400;

    float paddleHeight = 100;
    float paddleWidth = 20;

    Ball ball;
    Paddle user;
    Paddle comp;
    
    float compSpeed = 0;

    boolean gameover = false;
    String gameoverText;

    public void settings() {
        size(width, height);
        ball = new Ball();

        ball.x = width / 2;
        ball.y = height / 2;
    
        user = new Paddle();
    
        user.x = 50;
        user.y = height / 2;

        comp = new Paddle();
    
        comp.x = width - 50 - comp.width;
        comp.y = height / 2;
    }

    public void draw() {

        background(32);
        line(width / 2, 0, width / 2, height);

        rect(user.x, user.y, user.width, user.height);
        rect(comp.x, comp.y, comp.width, comp.height);
        circle(ball.x, ball.y, ball.width);

        println(ball.x + " " + ball.y + " " + user.y + " " + comp.y + " " + keyPressed);

        if (upPressed) {
            if (user.y > 0)
            {
                user.y -= 5;
            }
        }
        if (downPressed) {
            if (user.y < height - user.height)
            {
                user.y += 5;
            }
        }

        if (comp.y + paddleHeight * 3 / 8 > ball.y) {
            compSpeed = -1;
        }
        if (comp.y + paddleHeight * 5 / 8 < ball.y) {
            compSpeed = 1;
        }
        if (comp.y < 0) {
            comp.y = 0;
            compSpeed = 0;
        } 
        if (comp.y + comp.height > height) {
            comp.y = height - comp.height;
            compSpeed = 0;
        }

        comp.y += compSpeed;

        ball.x += ball.xSpeed;
        ball.y += ball.ySpeed;

        if (ball.x - ball.width / 2 < user.x + 20 && ball.x + ball.width / 2 > user.x + 20) {
            if (ball.y > user.y && ball.y < user.y + user.height) {
                ball.ySpeed = paddleHitYSpeed(ball.y - user.y);
                if (ball.xSpeed < 9) {
                    ball.xSpeed = (ball.xSpeed - 1) * -1;
                }
                else {
                    ball.xSpeed *= -1;
                }
            }
        }
        
        if (ball.x + ball.width / 2 > comp.x && ball.x - ball.width / 2 < comp.x) {
            if (ball.y > comp.y && ball.y < comp.y + comp.height) {
                ball.ySpeed = paddleHitYSpeed(ball.y - comp.y);
                if (ball.xSpeed < 9) {
                    ball.xSpeed = (ball.xSpeed + 1) * -1; 
                }
                else {
                    ball.xSpeed *= -1;
                }
            }
        }

        if (ball.y - ball.width / 2 < 0 || ball.y + ball.width / 2 > height) {
            ball.ySpeed *= -1;
        }

        if (ball.x - ball.width / 2 < 0) {
            gameover = true;
            gameoverText = "Comp won";
        }
        if (ball.x + ball.width / 2 > width) {
            gameover = true;
            gameoverText = "You won!";
        }

        if (gameover) {
            //fill(0, 0, 0);
            //rect(width / 2 - 30, height / 2 - 10, 60, 20);
            //fill(255, 255, 255);
            text(gameoverText, width / 2 - 29, height / 2 + 4);
        }
    }

    class Ball {
        float x;
        float y;
        float width = 10;
        float xSpeed = 1;
        float ySpeed = 1;
    }

    class Paddle {
        float x;
        float y;
        float width = paddleWidth;
        float height = paddleHeight;
    }

    float paddleHitYSpeed(float height) {
        return (height - paddleHeight / 2) / (paddleHeight / 4);
    }

    public void keyPressed() {
        if (keyCode == UP) {
            upPressed = true;
        }
        else if (keyCode == DOWN) {
            downPressed = true;
        }
    }

    public void keyReleased() {
        if (keyCode == UP) {
            upPressed = false;
        }
        else if (keyCode == DOWN) {
            downPressed = false;
        }
    }

    public static void main(String[] args)
    {
        String[] appletArgs = new String[] {"Pong"};
        Pong pong = new Pong();
        PApplet.runSketch(appletArgs, pong);      
    }
}