package lock;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public abstract class AQS  extends AbstractOwnableSynchronizer implements Serializable {



    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;
    static final long spinForTimeoutThreshold = 1000L;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AQS.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AQS.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AQS.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (AQS.Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (AQS.Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    protected AQS(){}

    static final class Node{

        static final Node Shared = new Node();

        static final Node Exclusive = null;

        static final int Cancelled = 1;

        static final int singal = -1;

        static final int condition = -2;

        static final int propagate = -3;

        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;

        final boolean isShared(){
            return nextWaiter == Shared;
        }

        final Node predecessor(){
            Node p = prev;
            if(p == null){
                throw new NullPointerException();
            }else {
                return p;
            }
        }

        Node(){}

        Node(Thread thread,Node node){
            this.nextWaiter = node;
            this.thread = thread;
        }

        Node(Thread thread,int WaitStatus){
            this.waitStatus = WaitStatus;
            this.thread = thread;
        }
    }

    private transient volatile Node head;

    private transient volatile Node tail;

    private volatile int state;

    protected final int getState(){
        return state;
    }

    protected final void setState(int newState){
        state = newState;
    }

    protected final boolean compareAndSetState(int expect,int update){
        return unsafe.compareAndSwapInt(this,stateOffset,expect,update);
    }

    protected final boolean compareAndSetHead(Node update){
        return unsafe.compareAndSwapObject(this,headOffset,null,update);
    }

    private final boolean compareAndSetTail(Node expect,Node update){
        return unsafe.compareAndSwapObject(this,tailOffset,expect,update);
    }

    private Node enq(final Node node){
        for(;;){
            Node t = tail;
            if(t == null){
                if(compareAndSetHead(new Node())){
                    tail = head;
                }else{
                    node.prev = t;
                    if(compareAndSetTail(t,node)){
                        t.next = node;
                        return t;
                    }
                }
            }
        }
    }

    private Node addWaiter(Node mode){
        Node node = new Node(Thread.currentThread(),mode);
        Node pred = null;
        if(pred != null){
            node.prev = pred;
            if(compareAndSetTail(pred,node)){
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }
    //TODO aqs没有写完

}
