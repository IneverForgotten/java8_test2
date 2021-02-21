package com.fanlm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 自定义服务端 ChannelHandler 处理消息
 */
@ChannelHandler.Sharable
public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 服务端接收客户端发送数据调用的方法
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf in = (ByteBuf) msg;
            System.out.println("message from client:" + in.toString(CharsetUtil.UTF_8));
            // 发送消息给客户端
            ctx.writeAndFlush(Unpooled.copiedBuffer("你也好！", CharsetUtil.UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
