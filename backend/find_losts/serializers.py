from rest_framework import serializers
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from .models import *
from user_account.models import User
from django.db.models import Count
import face_recognition
import numpy as np


class LostObjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = LostObject
        fields = ['date', 'city', 'user_id']


class LostItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = LostItem
        fields = ['id', 'type', 'serial_number', 'brand', 'color', 'description', 'image']

    def validate(self, attrs):
        if LostPerson.objects.filter(id=attrs.get('id', '')).exists():
            raise serializers.ValidationError({'id': {'id already exists'}})
        return super().validate(attrs)


class LostObjectSerializer(serializers.ModelSerializer):
    # lost_item = LostItemSerializer(many=True, read_only=True)
    class Meta:
        model = LostObject
        fields = ['id', 'date', 'city', 'is_matched', 'user_id']


class LostPersonImageSerializer(serializers.ModelSerializer):
    class Meta:
        model = LostPersonImage
        fields = ['image']


def match_with_found_person(pk):
    source_img = face_recognition.load_image_file(f'media/lostperson/{pk}.jpg')
    source_encoding = face_recognition.face_encodings(source_img)[0]
    founds = list(FoundPerson.objects.values_list('id'))

    encodings = []
    ids = []
    min_val = -1
    for id_f in founds:
        face = face_recognition.load_image_file(f'media/foundperson/{id_f[0]}.jpg')
        encodings.append(face_recognition.face_encodings(face)[0])
        ids.append(id_f[0])

    dist = face_recognition.face_distance(encodings, source_encoding)
    if dist.size > 0:
        min_val = min(dist)

    if 0 < min_val <= 0.52:
        return min_val, ids[dist.argmin()]
    else:
        return -1, -1


"""
    mini = 1.0
    minid = -1
    for id in ids:
        sum_dist = 0.0
        for img_num in range(id.number_of_images):
            face = face_recognition.load_image_file(f'media/foundperson/{source_id.pk}/{img_num + 1}.jpg')
            encoding = face_recognition.face_encodings(face)[0]
            dist = list(face_recognition.face_distance(source_encodings, encoding))
            sum_dist = sum_dist + sum(dist)

        avg = sum_dist/(source_id.number_of_images*id.number_of_images)
        if avg < mini:
            mini = avg
            minid = id

    if 0 < mini <= 0.52:
        return mini, minid
    else:
        return -1, -1
"""


class LostPersonSerializer(serializers.ModelSerializer):
    date = serializers.DateField()
    city = serializers.CharField(max_length=35)
    user_id = serializers.IntegerField()
    matched_with = serializers.IntegerField(default=0)

    class Meta:
        model = LostPerson
        fields = ['date', 'city', 'user_id', 'name', 'image', 'id', 'matched_with']
        read_only_fields = ['id', 'matched_with']

    def create(self, validated_data):
        data = validated_data.copy()
        data.pop('matched_with')
        # images_data = data.pop('images')
        # self.context.get('request').data.pop('images')
        user = User.objects.get(id=data.pop('user_id'))
        person_id = LostObject.objects.create(date=data.pop('date'), city=data.pop('city'), user_id=user)
        validated_data['id'] = person_id
        print(validated_data)
        person = None

        try:
            person = LostPerson.objects.create(id=person_id, **data)
        except TypeError:
            person_id.delete()
            raise TypeError('TypeError: LostPerson.objects.create()')
        """
        try:
            cnt = 0
            for img in images_data:
                cnt = cnt + 1
                image = LostPersonImage.objects.create(id_lp=person.pk, image_number=cnt, image=img)
                person.person_image.add(image)
        except TypeError:
            person = LostPerson.objects.get(id=person.id)
            obj = LostObject.objects.get(id=person.id)
            person.delete()
            obj.delete()
            raise TypeError('TypeError: LostPersonImage.objects.create()')
        """

        res_match = match_with_found_person(person.pk)
        print(res_match)
        matched = False
        if res_match[1] != -1:
            matched = True
            matched_person = FoundObject.objects.get(id=res_match[1])
            matched_person.is_matched = True
            matched_person.save()
            person_id.is_matched = True
            person_id.save()
            notify_l = Notification.objects.create(title="", description=f"", type=1, user_id=user)
            notify_f = Notification.objects.create(title="", description=f"", type=2, user_id=matched_person.user_id)
            matching = MatchedPerson.objects.create(id_fp=matched_person, id_lp=person_id, percent=1.0 - res_match[0],
                                                    notify_id_fp=notify_f, notify_id_lp=notify_l)

        if matched:
            validated_data['matched_with'] = matching
        else:
            validated_data['matched_with'] = 0

        return validated_data


class FoundObjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundObject
        fields = ['id', 'date', 'longitude', 'latitude', 'city', 'user_id', 'is_matched']


class FoundItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundItem
        fields = ['id', 'type', 'color', 'brand', 'description', 'serial_number', 'image']

    def validate(self, attrs):
        if FoundPerson.objects.filter(id=attrs.get('id', '')).exists():
            raise serializers.ValidationError({'id': {'id already exists'}})
        return super().validate(attrs)


def match_with_lost_person(pk):
    source_img = face_recognition.load_image_file(f'media/foundperson/{pk}.jpg')
    source_encoding = face_recognition.face_encodings(source_img)[0]
    losts = list(LostPerson.objects.values_list('id'))
    print(losts)
    encodings = []
    ids = []
    for id_l in losts:
        face = face_recognition.load_image_file(f'media/lostperson/{id_l[0]}.jpg')
        encodings.append(face_recognition.face_encodings(face)[0])
        ids.append(id_l[0])

    dist = face_recognition.face_distance(encodings, source_encoding)
    min_val = min(dist)

    if dist.size > 0:
        min_val = min(dist)

    if 0 < min_val <= 0.52:
        return min_val, ids[dist.argmin()]
    else:
        return -1, -1


class FoundPersonSerializer(serializers.ModelSerializer):
    date = serializers.DateField()
    longitude = serializers.DecimalField(max_digits=14, decimal_places=10, default=0.0)
    latitude = serializers.DecimalField(max_digits=14, decimal_places=10, default=0.0)
    city = serializers.CharField(max_length=35)
    user_id = serializers.IntegerField()
    matched_with = serializers.IntegerField(default=0)

    class Meta:
        model = FoundPerson
        fields = ['date', 'longitude', 'latitude', 'city', 'user_id', 'name', 'image', 'id', 'matched_with']
        read_only_fields = ['id', 'matched_with']

    def create(self, validated_data):
        data = validated_data.copy()
        data.pop('matched_with')
        # images_data = data.pop('images')
        # self.context.get('request').data.pop('images')
        user = User.objects.get(id=data.pop('user_id'))
        person_id = FoundObject.objects.create(date=data.pop('date'), longitude=data.pop('longitude'),
                                               latitude=data.pop('latitude'), city=data.pop('city'), user_id=user)
        validated_data['id'] = person_id
        person = None

        try:
            person = FoundPerson.objects.create(id=person_id, **data)
            print(person)
        except TypeError:
            person_id.delete()
            raise TypeError('TypeError: FoundPerson.objects.create()')
        """
        try:
            cnt = 0
            for img in images_data:
                cnt = cnt + 1
                image = LostPersonImage.objects.create(id_lp=person.pk, image_number=cnt, image=img)
                person.person_image.add(image)
        except TypeError:
            person = LostPerson.objects.get(id=person.id)
            obj = LostObject.objects.get(id=person.id)
            person.delete()
            obj.delete()
            raise TypeError('TypeError: LostPersonImage.objects.create()')
        """

        res_match = match_with_lost_person(person.pk)
        print(res_match)
        matched = False
        if res_match[1] != -1:
            matched = True
            matched_person = LostObject.objects.get(id=res_match[1])
            matched_person.is_matched = True
            matched_person.save()
            person_id.is_matched = True
            person_id.save()
            notify_f = Notification.objects.create(title="", description=f"", type=1, user_id=user)
            notify_l = Notification.objects.create(title="", description=f"", type=2, user_id=matched_person.user_id)
            matching = MatchedPerson.objects.create(id_lp=matched_person, id_fp=person_id, percent=1.0 - res_match[0],
                                                    notify_id_fp=notify_f, notify_id_lp=notify_l)
        if matched:
            validated_data['matched_with'] = matching
        else:
            validated_data['matched_with'] = 0

        return validated_data


class FoundPersonImageSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundPersonImage
        fields = ['id_image', 'image']


class MapSerializer(serializers.ModelSerializer):
    longitude = serializers.DecimalField(max_digits=14, decimal_places=10, source='id.longitude')
    latitude = serializers.DecimalField(max_digits=14, decimal_places=10, source='id.latitude')
    user_id = serializers.IntegerField(source='id.user_id.id')

    class Meta:
        model = FoundItem
        fields = ['longitude', 'latitude', 'user_id']
