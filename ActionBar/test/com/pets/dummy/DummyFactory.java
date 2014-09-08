package com.pets.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyFactory {

	public static final List<DummyReply> replyFactory(int counts) {
		return replyFactory(counts, "dummyUser", "dummyReplyContent");
	}

	public static final List<DummyReply> replyFactory(int counts,
			String username, String preContent) {

		List<DummyReply> replies = new ArrayList<DummyReply>();

		int idBegin = Math.round(1000);

		for (int i = 0; i < counts; i++) {
			DummyReply reply = new DummyReply();
			reply.setAgree(10);
			reply.setDisagree(100);
			reply.setId(idBegin + i);
			reply.setReplyDate(new Date());
			reply.setContent(preContent + i);
			reply.setReplyUser(username);
			replies.add(reply);
		}

		return replies;
	}

	public static final List<DummyDailyShareRecord> dailyShareRecordFactory(
			int counts, String username) {

		String preTitle = "dummyTitle:";
		String preContent = "dummyContent:";

		return dailyShareRecordFactory(counts, preTitle, preContent, username);
	}

	public static final List<DummyDailyShareRecord> dailyShareRecordFactory(
			int counts, String preTitle, String preContent, String username) {

		List<DummyDailyShareRecord> records = new ArrayList<DummyDailyShareRecord>();
		int idBegin = Math.round(1000);

		for (int i = 0; i < counts; i++) {
			DummyDailyShareRecord record = new DummyDailyShareRecord();
			record.setUsername(username);
			switch (i) {
			case 0:
				record.setImg("http://img1.she.thirsight.com/files/tsd/styles/large/public/201203/25/1332670457576.jpg");
				break;
			case 1:
				record.setImg("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
				break;
			case 2:
				record.setImg("http://down.111cn.net/uploads/allimg/091109/20025234E-0.jpg");
				break;
			case 3:
				record.setImg("http://sh30.microfotos.com/pic/0/43/4390/439045preview4.jpg");
				break;
			case 4:
				record.setImg("http://img1.she.thirsight.com/files/tsd/styles/large/public/201204/20/13349013133511.jpg");
				break;
			case 5:
				record.setImg("http://e.hiphotos.baidu.com/image/w%3D310/sign=cff50a4bb0119313c743f9b155390c10/a6efce1b9d16fdfa33e70267b68f8c5495ee7bb4.jpg");
				break;
			case 6:
				record.setImg("http://pic2.ooopic.com/01/38/73/59bOOOPIC72.jpg");
				break;
			case 7:
				record.setImg("http://img.sucai.redocn.com/attachments/images/201204/20120418/Redocn_2012041609254034.jpg");
				break;
			case 8:
				record.setImg("http://pic1.ooopic.com/uploadfilepic/yuanwenjian/2009-05-05/OOOPIC_meilifangcheng_20090505b9537ab148d4cc48.jpg");
				break;
			case 9:
				record.setImg("http://img12.3lian.com/gaoqing02/03/12/08.jpg");
				break;
			}

			record.setTime(new Date(System.currentTimeMillis()));
			record.setTitle(preTitle + i);
			record.setContent(preContent + i);
			records.add(record);
			record.setId(idBegin + i);
		}

		return records;
	}

	public static final List<DummyUser> userFactory(int counts, String headUrl) {

		return userFactory(counts, "dummyUsername:", headUrl);

	}

	public static final List<DummyUser> userFactory(int counts,
			String prefixName, String headUrl) {

		List<DummyUser> users = new ArrayList<DummyUser>();

		for (int i = 0; i < counts; i++) {
			DummyUser user = new DummyUser();
			user.setUsername(prefixName + i);
			user.setHeadUrl(headUrl);
			users.add(user);
		}

		return users;

	}
}
