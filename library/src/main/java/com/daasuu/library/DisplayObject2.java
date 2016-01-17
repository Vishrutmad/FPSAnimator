package com.daasuu.library;

import android.graphics.Canvas;

import com.daasuu.library.animator.ParabolicAnimator;
import com.daasuu.library.animator.TweenAnimator;

/**
 * DisplayObject class.
 * <p/>
 * When you only use default animation and drawing class which is provided by this library,
 * you can use simple interface to compose DisplayObject by calling {@link #with(Drawer)}.
 * <p/>
 * If you need to use your custom animation or drawing class which implements {@link Animator} or {@link Drawer},
 * you prepare their instances and simply set by calling {@link #animator(Animator)}.
 */
public class DisplayObject2 extends DisplayObject {

    private AnimParameter mAnimParameter;

    private Animator mAnimator;

    private Drawer mDrawer;

    /**
     * Return Composer instance to setup this DisplayObject instance.
     * This method is useful when you use only default class of animation.
     *
     * @param drawer
     * @return
     */
    public DisplayObjectComposer with(Drawer drawer) {
        mDrawer = drawer;
        return new DisplayObjectComposer();
    }

    /**
     * Set animation class.
     * Use this method only when there is need to your own custom class of animation,
     * in other cases, use {@link #with(Drawer)} instead.
     *
     * @param animator
     */
    public DisplayObject2 animator(Animator animator) {
        this.mAnimator = animator;
        mAnimParameter = mAnimator.getInitialAnimParameter();
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        mAnimator.setBaseLine(canvas, mDrawer.getWidth(), mDrawer.getHeight());
        mAnimator.updateAnimParam(mAnimParameter);
        mDrawer.draw(canvas, mAnimParameter.x, mAnimParameter.y, mAnimParameter.alpha, mAnimParameter.scaleX, mAnimParameter.scaleY, mAnimParameter.rotation);
    }

    @Override
    public void setUp(long fps) {
        mAnimator.setUp(fps);
    }

    public AnimParameter getAnimParameter() {
        return mAnimParameter;
    }

    /**
     * Composer provide simple composing interface.
     */
    public class DisplayObjectComposer {
        /**
         * @return tween composer
         */
        public TweenAnimator.Composer tween() {
            return TweenAnimator.composer(DisplayObject2.this);
        }

        /**
         * @return parabolic composer
         */
        public ParabolicAnimator.Composer parabolic() {
            return ParabolicAnimator.composer(DisplayObject2.this);
        }
    }
}

