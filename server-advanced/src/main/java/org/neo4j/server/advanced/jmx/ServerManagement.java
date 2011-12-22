/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.advanced.jmx;

import javax.management.NotCompliantMBeanException;

import org.neo4j.server.Bootstrapper;

public final class ServerManagement implements ServerManagementMBean
{
    private final Bootstrapper bs;

    public ServerManagement(Bootstrapper bs) throws NotCompliantMBeanException
    {
        this.bs = bs;
    }
    
    public void restartServer()
    {
        Thread thread = new Thread( "restart thread" )
        {
            @Override
            public void run()
            {
                bs.stop();
                bs.start();
            }
        };
        thread.setDaemon( false );
        thread.start();
        System.out.println("restarting server");
        try
        {
            thread.join();
        }
        catch ( InterruptedException e )
        {
            throw new RuntimeException( e );
        }
    }
}
