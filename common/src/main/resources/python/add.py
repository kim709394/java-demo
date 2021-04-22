#!/usr/bin/python3
import sys
def add(a,b):
    return (a + b);

if __name__ == '__main__':
    print(str(sys.argv));
    print(add(int(sys.argv[1]),int(sys.argv[2])));
    print("hello world");