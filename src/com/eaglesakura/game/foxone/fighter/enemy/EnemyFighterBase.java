package com.eaglesakura.game.foxone.fighter.enemy;

import android.graphics.Rect;

import com.eaglesakura.game.foxone.Define;
import com.eaglesakura.game.foxone.R;
import com.eaglesakura.game.foxone.bullet.BulletBase;
import com.eaglesakura.game.foxone.fighter.FighterBase;
import com.eaglesakura.game.foxone.scene.GameSceneBase;

public abstract class EnemyFighterBase extends FighterBase {

    /**
        * 生成されてからのフレームを記録する。
        */
    protected int frameCount = 0;
    
    public int game_score = 0;

    /**
     * 移動手段を列挙する
     * @author TAKESHI YAMASHITA
     *
     */
    public enum MoveType {
        /**
         * 直線的に動く
         */
        Straight,

        /**
         * 曲線的に動く
         */
        Curved,

        /**
         * 動かない
         */
        Not,
    }

    MoveType moveType = MoveType.Not;

    /**
     * 移動速度
     */
    float moveSpeed = 0;

    /**
     * 移動の基準点
     */
    float centerX = 0;

    /**
     * sinの増加速度
     */
    float sinSpeed = 0;

    /**
     * 現在のsinθの値
     */
    float theta = 0;

    /**
     * Cuve移動でのXの移動速度
     */
    float moveSpeedX = 0;

    public EnemyFighterBase(GameSceneBase scene) {
        super(scene);
    }

    /**
     * 直線移動をするように初期化する
     * @param moveSpeed
     */
    public void initMoveStraight(float moveSpeed) {
        moveType = MoveType.Straight;
        this.moveSpeed = moveSpeed;
    }

    /**
     * カーブ移動をするように初期化する
     * @param moveSpeedX Xの移動量。この値が大きいほど、左右に大きく動く
     * @param moveSpeedY Yの移動量。この値が大きいほど、上下の動きが速くなる
     * @param sinSpeed sinθの変動量。この値が大きいほど、左右のサイクルが短くなる
     */
    public void initMoveCurve(float moveSpeedX, float moveSpeedY, float sinSpeed) {
        moveType = MoveType.Curved;

        this.moveSpeedX = moveSpeedX;
        this.moveSpeed = moveSpeedY;
        this.sinSpeed = sinSpeed;

        // 現在の中心位置を記録する
        centerX = getPositionX();
    }

    /**
     * フレーム数のカウンタを0に戻す。
     */
    protected void resetFrameCount() {
        frameCount = 0;
    }

    @Override
    public void onDamage(BulletBase bullet) {
        super.onDamage(bullet);

        // ダメージを受けた結果、撃墜されたら、撃墜音を鳴らす
        if (isDead()) {
            scene.playSE(R.raw.dead);
        }
    }

    /**
     * 直線移動を行う
     */
    void onUpdateStraignt() {
        offsetPosition(0, moveSpeed);
    }

    /**
    * 左右のジグザグ移動を行う
    */
    void onUpdateCurved() {

        // 移動先のY座標は単純な加算でよい
        float nextY = getPositionY() + moveSpeed;

        // 移動先のX座標はsinから計算を行う
        float nextX = 0;
        {
            float xMove = (float) Math.sin(theta) * moveSpeedX;
            nextX = centerX + xMove;
            // sinの値をすすめる
            theta += sinSpeed;
        }

        // 求められた移動先座標を設定する
        setPosition(nextX, nextY);
    }

    @Override
    public void update() {
        switch (moveType) {
            case Straight:
                onUpdateStraignt();
                break;
            case Curved:
                onUpdateCurved();
                break;
        }
        ++frameCount;
    }

    @Override
    public void draw() {
        // 撃墜されている場合は描画を行わない
        if (isDead()) {
        	game_score++;
            return;
        }
        super.draw();
    }

    @Override
    public boolean isAppearedDisplay() {
        Rect spriteArea = sprite.getDstRect();

        if (spriteArea.right < 0) {
            // スプライトの右端が画面よりも左にある場合、画面外となる
            return false;
        }

        if (spriteArea.left > Define.VIRTUAL_DISPLAY_WIDTH) {
            // スプライトの左端が画面よりも右にある場合、画面外となる
            return false;
        }

        if (spriteArea.top > Define.VIRTUAL_DISPLAY_HEIGHT) {
            // スプライトの上端が画面よりも下にある場合、画面外となる
            return false;
        }

        // どれにも該当しない場合、画面内（もしくは画面上部）にいる
        return true;
    }
}