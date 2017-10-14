import os
import requests
import sys


urls = {
    'ringworm': ['http://www.dermnet.com/dn2/allJPG3/tinea-face','http://www.dermnet.com/dn2/allJPGThumb3/tinea-border','http://www.dermnet.com/dn2/allJPG3/tinea-scalp'],
    'vitiligo': ['http://www.dermnet.com/dn2/allJPG3/vitiligo'],
    'warts': ['http://www.dermnet.com/dn2/allJPGThumb3/warts','http://www.dermnet.com/dn2/allJPGThumb3/warts-common','http://www.dermnet.com/dn2/allJPGThumb3/warts-cryotherapy'],
    'eczema' : ['http://www.dermnet.com/dn2/allJPG3/eczema-leg','http://www.dermnet.com/dn2/allJPGThumb3/eczema-hand','http://www.dermnet.com/dn2/allJPGThumb3/eczema-face']
}


def main(n):
    for disease in urls.keys():
        if not os.path.exists(disease):
                    os.makedirs(disease)
        for count in range(1, n+1):
            print (disease," : ",count)
            for url in urls[disease]:
                prepend = '-%d.jpg' % (count)
                try:
                    q = requests.get(url + prepend)
                except exception as e:
                    print e

                filename = os.path.join(disease, str(count) + ".jpg")
                with open(filename, "w") as f:
                    f.write(q.content)


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Enter number of images to be downloaded.")
        sys.exit()

    n = int(sys.argv[1])
    main(n)
