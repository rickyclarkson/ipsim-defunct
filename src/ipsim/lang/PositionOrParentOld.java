package ipsim.lang;
/*
import ipsim.awt.*;
import ipsim.network.connectivity.*;
import org.jetbrains.annotations.*;
import fpeas.predicate.*;
import ipsim.network.*;

public final class PositionOrParent
{
	@Nullable
	public final Point point;

	@Nullable
	public final PacketSource parent;
    public static final PositionOrParent origin=position(Point.origin());

	public static final Predicate<PositionOrParent> isPosition=new Predicate<PositionOrParent>()
	{
		public boolean invoke(final PositionOrParent positionOrParent)
		{
			return positionOrParent.point!=null;
		}
	};

	private PositionOrParent(@Nullable final Point point,@Nullable final PacketSource right)
	{
		this.point=point;
		this.parent=right;
	}

	public static PositionOrParent position(@NotNull final Point position)
	{
		return new PositionOrParent(position,null);
	}

	public static @NotNull PositionOrParent parent(@NotNull final PacketSource parent)
	{
		return new PositionOrParent(null,parent);
	}
        }*/