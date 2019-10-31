package com.dcits.util;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;



    public class ShellUtil {
        private String ipAddress;

        private String username;

        private String password;

        public static final int DEFAULT_SSH_PORT = 22;  //默认是22

        private Vector<String> stdout;

        private static Logger logger = Logger.getLogger(ShellUtil.class);

        public ShellUtil(final String ipAddress, final String username, final String password) {
            this.ipAddress = ipAddress;
            this.username = username;
            this.password = password;
            stdout = new Vector<String>();
        }

        public int execute(final String command) {
            int returnCode = 0;
            JSch jsch = new JSch();

            try {
                // Create and connect session.
                Session session = jsch.getSession(username, ipAddress, DEFAULT_SSH_PORT);
                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");//去掉连接确认的
                session.connect(30000);

                // Create and connect channel.
                Channel channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                channel.setInputStream(null);
                BufferedReader input = new BufferedReader(new InputStreamReader(channel
                        .getInputStream()));

                channel.connect();
                logger.info("The remote command is: " + command);

                // Get the output of remote command.
                String line;
                while ((line = input.readLine()) != null) {
                    stdout.add(line);
                }
                input.close();

                // Get the return code only after the channel is closed.
                if (channel.isClosed()) {
                    returnCode = channel.getExitStatus();
                }

                // Disconnect the channel and session.
                channel.disconnect();
                session.disconnect();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnCode;
        }

        public static boolean executeRemoteShell(String ip, String username, String passwd, String command) {
            ShellUtil sshExecutor = new ShellUtil(ip, username, passwd);
            int result = sshExecutor.execute(command);
            Vector<String> stdout = sshExecutor.getStandardOutput();
            for (String str : stdout) {
                System.out.println(str);
                logger.info(str);
            }
            if (result == 0)
                return true;
            else
                return false;
        }
        public Vector<String> getStandardOutput() {
            return stdout;
        }

}
