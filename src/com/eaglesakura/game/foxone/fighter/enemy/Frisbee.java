package com.eaglesakura.game.foxone.fighter.enemy;

import com.eaglesakura.game.foxone.R;
import com.eaglesakura.game.foxone.bullet.FrisbeeBullet;
import com.eaglesakura.game.foxone.bullet.SnipeBullet;
import com.eaglesakura.game.foxone.scene.GameSceneBase;
import com.eaglesakura.game.foxone.scene.PlaySceneBase;

public class Frisbee extends EnemyFighterBase {
    public enum AttackType {
        /**
        * まっすぐに弾を撃つ
        */
        ShotStraight,

        /**
        * プレイヤーを狙撃する
        */
        Snipe,

        /**
        * 何もしない
        */
        Not,
    }

    /**
     * 攻撃手段
     */
    AttackType attackType = AttackType.Not;

    public Frisbee(AttackType attackType, GameSceneBase scene) {
        super(scene);

        // 攻撃手段を保持する
        this.attackType = attackType;

        // 攻撃手段によって、敵の見た目を変化させる
        switch (attackType) {
            case ShotStraight:
                sprite = loadSprite(R.drawable.enemy_00_y); // 黄フリスビーを読み込む
                hp = 1;
                break;
            case Snipe:
                sprite = loadSprite(R.drawable.enemy_00_g); // 緑フリスビーを読み込む
                hp = 1;
                break;
            default:
                sprite = loadSprite(R.drawable.enemy_00); // 赤フリスビーを読み込む
                hp = 1;
                break;
        }
    }

    /**
     * まっすぐ弾を撃つ場合の更新
     */
    void updateStraight() {

        // 指定したフレームで処理を行わせる
        if (frameCount == 30 * 3) {
            // 150フレーム経過したら弾を撃って行動カウンターをリセットする。
            FrisbeeBullet bullet = new FrisbeeBullet(scene, this);
            ((PlaySceneBase) scene).addBullet(bullet);

            resetFrameCount();
        }
    }

    /**
    * 狙撃する場合の更新
    */
    void updateSnipe() {
        // 指定したフレームで処理を行わせる
        if (frameCount == 30 * 3) {
            SnipeBullet bullet = new SnipeBullet(scene, this);
            bullet.setup(((PlaySceneBase) scene).getPlayer(), 20);

            ((PlaySceneBase) scene).addBullet(bullet);

            resetFrameCount();
        }
    }

    @Override
    public void update() {
        // スーパークラスの処理を行わせる
        super.update();

        switch (attackType) {
            case ShotStraight:
                updateStraight();
                break;
            case Snipe:
                updateSnipe();
                break;
            default:
                // 何もしないパターン
                break;
        }
    }
}