package com.eaglesakura.game.foxone.scene;

import com.eaglesakura.game.foxone.Define;
import com.eaglesakura.game.foxone.FoxOne;
import com.eaglesakura.game.foxone.R;
import com.eaglesakura.lib.android.game.graphics.Sprite;
import com.eaglesakura.lib.android.game.input.MultiTouchInput;
import com.eaglesakura.lib.android.game.scene.SceneBase;
import com.eaglesakura.lib.android.game.scene.SceneManager;

public class RankingTitle extends GameSceneBase {
    protected RankingTitle(FoxOne game) {
		super(game);
		// TODO 自動生成されたコンストラクター・スタブ
	}


    Sprite ranking = null;
    Sprite back = null;
    Sprite iti = null;
    Sprite ni = null;
    Sprite san = null;
    Sprite yon = null;
    Sprite go = null;


    @Override
    public void onSceneStart(SceneManager manager, SceneBase before) {


            ranking = new Sprite(loadImageDrawable(R.drawable.game_logo_ranking));
            ranking.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 -400);

            iti = new Sprite(loadImageDrawable(R.drawable.rank_1st));
            iti.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 -200);

            ni = new Sprite(loadImageDrawable(R.drawable.rank_2nd));
            ni.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 -100);

            san = new Sprite(loadImageDrawable(R.drawable.rank_3th));
            san.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 );

            yon = new Sprite(loadImageDrawable(R.drawable.rank_4th));
            yon.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 +100);

            go = new Sprite(loadImageDrawable(R.drawable.rank_5th));
            go.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 +200);


            back = new Sprite(loadImageDrawable(R.drawable.game_botton_back));
            back.setSpritePosition(Define.VIRTUAL_DISPLAY_WIDTH / 2, Define.VIRTUAL_DISPLAY_HEIGHT / 2 + 400);




    }



    @Override
    public void onSceneExit(SceneManager manager, SceneBase next) {
    }

    @Override
    public void onFrameBegin(SceneManager manager) {
    }

    @Override
    public void onFrameDraw(SceneManager manager) {
        // 背景を宇宙色に染める
        getSpriteManager().clear(255, 255, 185, 255);

        // 各画像の描画
        {
            getSpriteManager().draw(ranking);
            getSpriteManager().draw(back);
            getSpriteManager().draw(iti);
            getSpriteManager().draw(ni);
            getSpriteManager().draw(san);
            getSpriteManager().draw(yon);
            getSpriteManager().draw(go);
        }
    }

    @Override
    public void onFrameEnd(SceneManager manager) {

        MultiTouchInput input = game.getMultiTouchInput();
        if (back.isIntersectTouchOnce(input) != null) {
            manager.setNextScene(new TitleScene(game));
        }
    }

    @Override
    public void onGamePause(SceneManager manager) {
    }

    @Override
    public void onGameResume(SceneManager manager) {
    }

}