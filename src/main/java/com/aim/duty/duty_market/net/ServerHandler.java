package com.aim.duty.duty_market.net;

import org.apache.mina.core.session.IoSession;

import com.aim.duty.duty_market.navigation.ActionNavigation;
import com.aim.game_base.entity.net.base.Protocal.CS;
import com.aim.game_base.navigation.ActionSupport;
import com.aim.game_base.net.IoHandlerAdapter;

//import byCodeGame.game.cache.local.RoleCache;
//import byCodeGame.game.entity.bo.Role;
//import byCodeGame.game.error.NumberFakeException;
//import byCodeGame.game.navigation.ActionSupport;
//import byCodeGame.game.navigation.Navigation;

/**
 * 消息处理器
 * 
 */
public class ServerHandler extends IoHandlerAdapter {

	// 当一个客户端连结进入时
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress() + "打开连接");
	}

	// 当一个 新的session创建时
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress() + "创建新的 会话");
	}

	// 当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
//		if (null != session) {
//			Integer roleInteger = (Integer) session.getAttribute("roleId");
//			Role role = new Role();
//			if (roleInteger != 0) {
//				role = RoleCache.getRoleById(roleInteger);
//			}
//			if (role != null) {
//				System.out.println("玩家正常断开数据处理，用户ID：" + role.getRoleId());
//				session.close(true);
//				SessionCloseHandler.manipulate(role);
//			}
//		}
		System.out.println("market system 用户断开");
	}

	// 异常捕获
	@Override
	public void exceptionCaught(IoSession session, Throwable e) throws Exception {

		if (e.getMessage().equals("远程主机强迫关闭了一个现有的连接。")) {
			System.out.println(e.getMessage());
			this.sessionClosed(session);
		} else {
			System.err.println("程序业务逻辑出现异常,已处理该账户信息,并强制下线！" + (Integer) session.getAttribute("roleId"));
			e.printStackTrace();
			this.sessionClosed(session);
		}
	}

	// 当客户端发送的消息到达时
	@Override
	public void messageReceived(IoSession session, Object messageObj) throws Exception {
//		Message message = (Message) messageObj;
//
//		if (null == message) {
//			System.out.println("ERR_MESSAGE_REC");
//			return;
//		}
//		ActionSupport action = Navigation.getAction(message.getType());
//		Integer roleId = (Integer) session.getAttribute("roleId");
//		if (message.getType() != (short) NavigationModule.HEART_LINK)
//			System.out.println("服务器接收用户：" + roleId + "的消息，请求指令号为:" + message.getType());
//
//		// 如果协议必须有玩家
//		if (roleId == null && !NavigationModule.ALLOW_NO_ROLE_PROTOCAL.contains(message.getType())) {
//			System.out.println(message.getType()+" 必须要有玩家");
//			
//			Message msg= new Message();
//			msg.setType(LoginConstant.RELOGION);
//			session.write(msg);
//			
//			return;
//		}
//
//		try {
//			action.execute(message, session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("伪造的协议ID：" + message.getType());
//			// 判断是否属于无效的协议号 TODO 处理方式可能需要变更
//			// if(e.getClass().equals(NumberFakeException.class))
//			// {
////			 session.close(true);
//			// }
//		}
		
		
		CS message = (CS) messageObj;

		if (null == message) {
			System.out.println("ERR_MESSAGE_REC");
			return;
		}
		ActionSupport action = ActionNavigation.getAction(message.getProtocal());
		Integer roleId = (Integer) session.getAttribute("roleId");

		System.out.println("服务器接收用户：" + roleId + "的消息，请求指令号为:" + message.getProtocal());

		try {
			action.execute(message.getData(), session);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("伪造的协议ID：" + message.getProtocal());
			// 判断是否属于无效的协议号 TODO 处理方式可能需要变更
			// if(e.getClass().equals(NumberFakeException.class))
			// {
			session.close(true);
			// }
		}
	}

	// 当发送消息成功时调用这个方法，注意这里的措辞，发送成功之后，也就是说发送消息是不能用这个方法的。

	@Override
	public void messageSent(IoSession session, Object message) {
//		Integer roleId = (Integer) session.getAttribute("roleId");
//		Message msg = (Message) message;
//		//
//		// Role role = RoleCache.getRoleBySession(session);
//		// if(role != null)
//		// {
//		// //用户操作添加到记录
//		// role.addPlayerOperation(String.valueOf(msg.getType()));
//		// }
//		// 打印发出的信息
//		// if(msg.getType() != (short)20303)
//		// {
//		PrintToClientMsg.printIOBuffer(roleId, msg);
//		// }
//		//
	}
}
