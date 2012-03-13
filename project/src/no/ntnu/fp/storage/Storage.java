/*
 * Created on Sep 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.fp.storage;

import java.io.IOException;
import java.text.ParseException;
import java.net.URL;

import no.ntnu.fp.model.Workgroup;

/**
 * @author tho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Storage {

	Workgroup load(URL url) throws IOException, ParseException;
	
	void save(URL url, Workgroup project) throws IOException, ParseException;

}
