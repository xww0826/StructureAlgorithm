package com.xww.notes.learn_opengl.sample.glsurfaceview.shape;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Rectangle extends ShaderProgram {

    private final FloatBuffer vertexBuffer;

    // 每个顶点的坐标数量
    private static final int PER_VERTEX = 3;
    // 矩形四个点的坐标值(逆时针方向,在 3D 坐标系中,方向决定了哪面是正面)
    private static final float RECTANGLE_VERTEXES[] = {
            -0.5f, 0.5f, 0.0f, // 左上顶点
            -0.5f, -0.5f, 0.0f, // 左下顶点
            0.5f, -0.5f, 0.0f,  // 右下顶点
            0.5f, 0.5f, 0.0f // 右上顶点
    };

    public Rectangle(String vertShader, String fragShader) {
        super(vertShader, fragShader);
        // 初始化顶点坐标数据，坐标点的数目 * float所占字节
        ByteBuffer bb = ByteBuffer.allocateDirect(RECTANGLE_VERTEXES.length * FLOAT_BYTE);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        // 把坐标添加到FloatBuffer
        vertexBuffer.put(RECTANGLE_VERTEXES);
        // 设置 buffer 的位置为起始点0
        vertexBuffer.position(0);
    }

    @Override
    public void draw() {
        // 激活着色器程序,把程序添加到 OpenGL 环境
        GLES20.glUseProgram(mProgram);
        // 获取顶点着色器中的 vPosition 变量(因为之前已经编译过着色器代码,所以可以从着色器程序中获取)
        int position = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(position);
        // 将顶点数据传递给 position 指向的 vPosition 变量,将顶点属性与顶点缓冲对象关联
        GLES20.glVertexAttribPointer(position, PER_VERTEX, GLES20.GL_FLOAT, false, PER_VERTEX * FLOAT_BYTE, vertexBuffer);
        // 获取片段着色器中的 vColor 变量
        int color = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 通过 color 设置绘制的颜色值
        GLES20.glUniform4fv(color, 1, fillColor, 0);
        // 绘制顶点数组
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, RECTANGLE_VERTEXES.length / PER_VERTEX);
        // 操作完后,取消允许操作顶点对象 position
        GLES20.glDisableVertexAttribArray(position);
    }
}
    