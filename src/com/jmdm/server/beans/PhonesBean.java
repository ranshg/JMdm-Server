package com.jmdm.server.beans;

import java.sql.Connection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static com.jmdm.server.entities.Tables.*;
import com.jmdm.server.entities.tables.records.DevicesRecord;

@ManagedBean
@SessionScoped
public class PhonesBean {

	DevicesRecord[] devices = null;
	private boolean devicesUpdated = false;
	
	public String logout() {
		System.out.println("in logout()");
		return "index?faces-redirect=true";
	}
	
	public DevicesRecord[] fetchDevices() {
		System.out.println("in fetchDevices()");
		devices = null;
		Connection conn = null;
		try {
			conn = LoginBean.getDbConnection();
			
			DSLContext context = DSL.using(conn, SQLDialect.SQLITE);
			devices = context.selectFrom(DEVICES)
					.orderBy(DEVICES.IMEI)
					.fetch()
					.toArray(new DevicesRecord[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LoginBean.closeConnection(conn);
		}
		devicesUpdated = false;
		return devices;
	}
	
	public DevicesRecord[] getDevices() {
		System.out.println("in getDevices()");
		if (devices == null || devicesUpdated) {
			return fetchDevices();
		}
		
		return devices;
	}
}
