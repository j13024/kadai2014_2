package com.eaglesakura.game.foxone.fighter;

import android.graphics.Rect;

import com.eaglesakura.game.foxone.Define;
import com.eaglesakura.game.foxone.R;
import com.eaglesakura.game.foxone.bullet.Bomb;
import com.eaglesakura.game.foxone.bullet.BulletBase;
import com.eaglesakura.game.foxone.bullet.PlayerBullet;
import com.eaglesakura.game.foxone.input.AttackButton;
import com.eaglesakura.game.foxone.input.JoyStick;
import com.eaglesakura.game.foxone.scene.GameSceneBase;
import com.eaglesakura.game.foxone.scene.PlaySceneBase;
import com.eaglesakura.lib.android.game.input.MultiTouchInput;
import com.eaglesakura.lib.android.game.input.MultiTouchInput.TouchPoint;
import com.eaglesakura.lib.android.game.math.Vector2;

public class PlayerFighter extends FighterBase {

    /**
     * 操作用のジョイスティック
     */
    JoyStick joyStick;

    /**
     * 攻撃ボタン
     */
    AttackButton shotButton;

    /**
     * ボムボタン
     */
    AttackButton bombButton;

    /**
     * 無敵の残り時間
     */
    int mutekiFrame = 0;

    /**
     * 通常弾のチャージ時間残量
     */
    int bulletChargeFrame = 0;

    /**
     * 残りのボム数。
     */
    int bombCount = 3;

    /**
     * 最大HPを定義する
     */
    static final int MAX_HP = 10;

    Rect touchArea = new Rect(0, 0, 800, Define.VIRTUAL_DISPLAY_HEIGHT);

    public PlayerFighter(GameSceneBase scene, JoyStick joyStick, AttackButton shotButton) {
        super(scene);
        this.joyStick = joyStick;
        this.shotButton = shotButton; // 攻撃ボタンを保持する
        // プレイヤー画像の読み込み
        sprite = loadSprite(R.drawable.player);

        // 初期位置を画面の下側中央にする
        setPosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT - 100);

        // プレイヤーのHPを10にする
        hp = MAX_HP;
    }

    /**
     * 残りHPが何パーセントか取得する。
     * 戻り値は0〜100となる。
     * @return
     */
    public int getHPPercent() {
        return 100 * hp / MAX_HP;
    }

    /**
     * ボムの残数を取得する
     * @return
     */
    public int getBombCount() {
        return bombCount;
    }

    /**
     * ボムボタンを登録する
     * @param bombButton
     */
    public void setBombButton(AttackButton bombButton) {
        this.bombButton = bombButton;
    }

    /**
     * プレイヤー位置を更新する
     */
    MultiTouchInput touch = scene.getMultiTouchInput();








    void updatePosition() {
        // 移動させたい向きのベクトルを取得する
        Vector2 move = joyStick.getMoveVector();

        // 1フレームで最大5ピクセル移動するようにする
        move.mul(5.0f);


        // その方向へ移動させる
        offsetPosition(move.x, move.y);


        //! 位置をプレイエリア内に補正する
        correctPosition();
    }

    @Override
    public void onDamage(BulletBase bullet) {
        super.onDamage(bullet);

        // 無敵の残り時間を設定する。
        // デフォルトは1秒間。
        mutekiFrame = 30;
    }

    @Override
    public Rect getIntersectRect() {
        if (mutekiFrame > 0) {
            // 無敵の残り時間が残っているため、衝突判定は発生しない
            return null;
        }
        return super.getIntersectRect();
    }

    /**
     * 弾を発射し、ステージへ追加する。
     */
    void fire() {
        if (bulletChargeFrame > 0) {
            // 弾のチャージ中だったら発射できない
            return;
        }

        PlayerBullet bullet = new PlayerBullet(scene, this);
        ((PlaySceneBase) scene).addBullet(bullet);

        // 弾を撃ったから、チャージ時間を設定する
        bulletChargeFrame = 10;
    }

    void fireBomb() {

        if (bombCount <= 0) {
            // ボムの残弾が残っていない場合、発射できない
            return;
        }

        Bomb bomb = new Bomb(scene, this);
        ((PlaySceneBase) scene).addBullet(bomb);

        // ボムの残弾を減らす
        --bombCount;
    }

    int i = 0;

    @Override
    public void update() {

    	if(i == 1)updatePosition();

    	MultiTouchInput touch = scene.getMultiTouchInput();
        TouchPoint touchPoint = touch.getEnableTouchPoint(touchArea); // 指定したエリアに触れている指があるか調べる

        if (touchPoint != null) {
            sprite.setSpritePosition(touchPoint.getCurrentX(), touchPoint.getCurrentY()-50); // 指が置かれているため、その位置へジョイスティックを移動する
            onsetPosition(touchPoint.getCurrentX(), touchPoint.getCurrentY()-50);
        }

        if (shotButton.isAttack()) {
            fire();
        }

        if (bombButton != null) {
            if (bombButton.isAttack()) {
                fireBomb();
            }
        }

        // 無敵の残り時間を減らす
        --mutekiFrame;

        // チャージの残り時間を減らす
        --bulletChargeFrame;
    }

    @Override
    public void draw() {
        if (mutekiFrame > 0) {
            // 無敵時間が残っている場合、点滅させる
            if (mutekiFrame % 2 == 0) {
                super.draw();
            }
        } else {
            super.draw();
        }
    }
}