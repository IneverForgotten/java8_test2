package com.fanlm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


/**
 * 自定义客户端 ChannelHandler 处理消息
 */
@ChannelHandler.Sharable
public class HelloClientHandler extends ChannelInboundHandlerAdapter {

    private final String message;

    public HelloClientHandler(String message) {
        this.message = message;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("client sen msg to server " + message);
        ctx.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println("client receive msg from server: " + in.toString(CharsetUtil.UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 处理客户端消息发生异常的时候被调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("error info");
        cause.printStackTrace();
        ctx.close();
    }
}
