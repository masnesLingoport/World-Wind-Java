/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.animation;

import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.util.*;

/**
 * @author jym
 * @version $Id$
 */
public class PositionAnimator extends BasicAnimator
{
    
    protected Position begin;
    protected Position end;
    protected final PropertyAccessor.PositionAccessor propertyAccessor;

    public PositionAnimator(
        Interpolator interpolator,
        Position begin,
        Position end,
        PropertyAccessor.PositionAccessor propertyAccessor)
    {
        super(interpolator);
        if (interpolator == null)
        {
           this.interpolator = new ScheduledInterpolator(10000);
        }
        if (begin == null || end == null)
        {
           String message = Logging.getMessage("nullValue.PositionIsNull");
           Logging.logger().severe(message);
           throw new IllegalArgumentException(message);
        }
        if (propertyAccessor == null)
        {
           String message = Logging.getMessage("nullValue.ViewPropertyAccessorIsNull");
           Logging.logger().severe(message);
           throw new IllegalArgumentException(message);
        }
        
        
        String message = "Creating a new Position Animator";
        Logging.logger().info(message);

        String details_one = "This is start: " + this.begin.toString()
            + ", with a concatenation and end: " + this.end.toString() + "\n";
        Logging.logger().info(details);
        
        this.begin = begin;
        this.end = end;
        this.propertyAccessor = propertyAccessor;
    }

    public String describe()
    {
        String description = Logging.getMessage("description.PositionAnimator");
        return description;
    }

    public void setBegin(Position begin)
    {
        this.begin = begin;
    }

    public void setEnd(Position end)
    {
        this.end = end;
    }

    public Position getBegin()
    {
        return this.begin;
    }

    public Position getEnd()
    {
        return this.end;
    }

    public PropertyAccessor.PositionAccessor getPropertyAccessor()
    {
        return this.propertyAccessor;
    }

    protected void setImpl(double interpolant)
    {
        Position newValue = this.nextPosition(interpolant);
        if (newValue == null)
           return;

        boolean success = this.propertyAccessor.setPosition(newValue);
        if (!success)
        {
           this.flagLastStateInvalid();
        }
        if (interpolant >= 1)
        {
            this.stop();
        }
    }

    protected Position nextPosition(double interpolant)
    {
        return Position.interpolateGreatCircle(interpolant, this.begin, this.end);
    }
}
