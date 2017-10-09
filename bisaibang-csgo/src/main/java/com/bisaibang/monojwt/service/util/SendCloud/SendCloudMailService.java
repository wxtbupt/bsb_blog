package com.bisaibang.monojwt.service.util.SendCloud;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bisaibang.monojwt.web.rest.task.MSTaskResource;
import com.sendcloud.sdk.builder.SendCloudBuilder;
import com.sendcloud.sdk.core.SendCloud;
import com.sendcloud.sdk.model.AddressListReceiver;
import com.sendcloud.sdk.model.MailAddressReceiver;
import com.sendcloud.sdk.model.MailBody;
import com.sendcloud.sdk.model.SendCloudMail;
import com.sendcloud.sdk.model.TemplateContent;
import com.sendcloud.sdk.model.TextContent;
import com.sendcloud.sdk.model.TextContent.ScContentType;
import com.sendcloud.sdk.util.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendCloudMailService {
    private final static Logger log = LoggerFactory.getLogger(MSTaskResource.class);

	public static void send_common(String toAddr) throws Throwable {
        final String url = "http://api.sendcloud.net/apiv2/mail/send";
        final String apiUser = "weiwensangsang_test_m1lK7b";
        final String apiKey = "fpM207UirghurFoQ";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(url);

        List params = new ArrayList();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", "match@allied-esports.com"));
        params.add(new BasicNameValuePair("fromName", "守望公开赛"));
        params.add(new BasicNameValuePair("to", toAddr));
        params.add(new BasicNameValuePair("subject", "报名成功！"));
        params.add(new BasicNameValuePair("html", "队伍信息提交成功，请等待审核。若队伍不合格会有工作人员与您联系。详情请访问官方网站：https://owod.allied-esports.cn/"));

        httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httPost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
        httPost.releaseConnection();
    }

    public static void send_reform_invite(String toAddr) throws Throwable {
        final String url = "http://api.sendcloud.net/apiv2/mail/send";
        final String apiUser = "weiwensangsang_test_m1lK7b";
        final String apiKey = "fpM207UirghurFoQ";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(url);

        List params = new ArrayList();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", "match@allied-esports.com"));
        params.add(new BasicNameValuePair("fromName", "守望公开赛"));
        params.add(new BasicNameValuePair("to", toAddr));
        params.add(new BasicNameValuePair("subject", "邀请报名"));
        params.add(new BasicNameValuePair("html", "邀请您参加守望公开赛，请登录https://owod.allied-esports.cn/查看站内信。"));

        httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httPost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
        httPost.releaseConnection();
    }

    public static void send_reform_agree(String toAddr) throws Throwable {
        final String url = "http://api.sendcloud.net/apiv2/mail/send";
        final String apiUser = "weiwensangsang_test_m1lK7b";
        final String apiKey = "fpM207UirghurFoQ";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(url);

        List params = new ArrayList();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", "match@allied-esports.com"));
        params.add(new BasicNameValuePair("fromName", "守望公开赛"));
        params.add(new BasicNameValuePair("to", toAddr));
        params.add(new BasicNameValuePair("subject", "队员加入"));
        params.add(new BasicNameValuePair("html", "有队员加入您的小队，请登录https://owod.allied-esports.cn/查看站内信。"));

        httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httPost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
        httPost.releaseConnection();
    }

    public static void send_reform_delete(String toAddr) throws Throwable {
        final String url = "http://api.sendcloud.net/apiv2/mail/send";
        final String apiUser = "weiwensangsang_test_m1lK7b";
        final String apiKey = "fpM207UirghurFoQ";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(url);

        List params = new ArrayList();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", "match@allied-esports.com"));
        params.add(new BasicNameValuePair("fromName", "守望公开赛"));
        params.add(new BasicNameValuePair("to", toAddr));
        params.add(new BasicNameValuePair("subject", "被请出队伍"));
        params.add(new BasicNameValuePair("html", "您被请出申请的小队，请登录https://owod.allied-esports.cn/查看站内信。"));

        httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httPost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
        httPost.releaseConnection();
    }

    public static void send_common_advanced() throws Throwable {
		MailAddressReceiver receiver = new MailAddressReceiver();
		// 添加收件人
		receiver.addTo("a@ifaxin.com");
		// 添加抄送
		receiver.addCc("b@ifaxin.com");
		// 添加密送
		receiver.addBcc("c@ifaxin.com");

		MailBody body = new MailBody();
		// 设置 From
		body.setFrom("sendcloud@sendcloud.org");
		// 设置 FromName
		body.setFromName("SendCloud");
		// 设置 ReplyTo
		body.setReplyTo("reply@sendcloud.org");
		// 设置标题
		body.setSubject("来自 SendCloud SDK 的邮件");
		// 创建文件附件
		body.addAttachments(new File("D:/1.png"));

		// 配置 Xsmtpapi 扩展字段
		List<String> toList = new ArrayList<String>();
		toList.add("d@ifaxin.com");
		toList.add("e@ifaxin.com");
		List<String> moneyList = new ArrayList<String>();
		moneyList.add("1000");
		moneyList.add("2000");
		List<String> nameList = new ArrayList<String>();
		nameList.add("a");
		nameList.add("b");
		Map<String, List<String>> sub = new HashMap<String, List<String>>();
		sub.put("%name%", nameList);
		sub.put("%money%", moneyList);
		// 此时, receiver 中添加的 to, cc, bcc 均会失效
		body.addXsmtpapi("to", toList);
		body.addXsmtpapi("sub", sub);
		body.addHeader("SC-Custom-test_key1", "test1");
		body.addHeader("NO-SC-Custom-test_key1", "test2");

		TextContent content = new TextContent();
		content.setContent_type(ScContentType.html);
		content.setText("<html><p>亲爱的 %name%: </p> 您本月的支出为: %money% 元.</p></html>");

		SendCloudMail mail = new SendCloudMail();
		mail.setTo(receiver);
		mail.setBody(body);
		mail.setContent(content);

		SendCloud sc = SendCloudBuilder.build();
		ResponseData res = sc.sendMail(mail);
		System.out.println(res.getResult());
		System.out.println(res.getStatusCode());
		System.out.println(res.getMessage());
		System.out.println(res.getInfo());
	}

	public static void send_template() throws Throwable {
		MailBody body = new MailBody();
		// 设置 From
		body.setFrom("sendcloud@sendcloud.org");
		// 设置 FromName
		body.setFromName("SendCloud");
		// 设置 ReplyTo
		body.setReplyTo("reply@sendcloud.org");
		// 设置标题
		body.setSubject("来自 SendCloud SDK 的邮件");
		// 创建文件附件
		body.addAttachments(new File("D:/1.png"));

		List<String> toList = new ArrayList<String>();
		toList.add("a@ifaxin.com");
		toList.add("b@ifaxin.com");
		List<String> moneyList = new ArrayList<String>();
		moneyList.add("1000");
		moneyList.add("3000");
		List<String> nameList = new ArrayList<String>();
		nameList.add("a");
		nameList.add("b");
		Map<String, List<String>> sub = new HashMap<String, List<String>>();
		sub.put("%name%", nameList);
		sub.put("%code%", moneyList);
		// 此时, receiver 中添加的 to, cc, bcc 均会失效
		body.addXsmtpapi("to", toList);
		body.addXsmtpapi("sub", sub);
		body.addHeader("SC-Custom-test_key1", "test1");
		body.addHeader("NO-SC-Custom-test_key1", "test2");

		// 使用邮件模板
		TemplateContent content = new TemplateContent();
		content.setTemplateInvokeName("sendcloud_account_bind");

		SendCloudMail mail = new SendCloudMail();
		// 模板发送时, 必须使用 Xsmtpapi 来指明收件人; mail.setTo();
		mail.setBody(body);
		mail.setContent(content);

		SendCloud sc = SendCloudBuilder.build();
		ResponseData res = sc.sendMail(mail);
		System.out.println(res.getResult());
		System.out.println(res.getStatusCode());
		System.out.println(res.getMessage());
		System.out.println(res.getInfo());
	}

	public static void send_with_addresslist() throws Throwable {
		AddressListReceiver receiver = new AddressListReceiver();
		// 设置地址列表
		receiver.addTo("liubidatest@maillist.sendcloud.org");

		MailBody body = new MailBody();
		// 设置 From
		body.setFrom("sendcloud@sendcloud.org");
		// 设置 FromName
		body.setFromName("SendCloud");
		// 设置 ReplyTo
		body.setReplyTo("reply@sendcloud.org");
		// 设置标题
		body.setSubject("来自 SendCloud SDK 的邮件");
		// 创建文件附件
		body.addAttachments(new File("D:/1.png"));

		// 使用邮件模板
		TemplateContent content = new TemplateContent();
		content.setTemplateInvokeName("sendcloud_account_bind");

		SendCloudMail mail = new SendCloudMail();
		mail.setTo(receiver);
		mail.setBody(body);
		mail.setContent(content);

		SendCloud sc = SendCloudBuilder.build();
		ResponseData res = sc.sendMail(mail);
		System.out.println(res);
	}
}
