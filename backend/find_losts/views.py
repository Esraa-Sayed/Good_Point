from django.shortcuts import render
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from rest_framework import serializers
from django.http import HttpResponse
from .models import LostObject, LostItem, LostPerson, LostPersonImage
from rest_framework import status
from .serializers import *
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.reverse import reverse
from rest_framework import status
from rest_framework import viewsets
from rest_framework import permissions

# Create your views here.
class LostObjectView(generics.ListCreateAPIView):
    queryset = LostObject.objects.all()
    serializer_class = LostObjectSerializer


class LostItemView(generics.ListCreateAPIView):
    queryset = LostItem.objects.all()
    serializer_class = LostItemSerializer

    def post(self, request):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_204_NO_CONTENT)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class LostPersonView(generics.ListCreateAPIView):
    queryset = LostPerson.objects.all()
    serializer_class = LostPersonSerializer
    def post(self, request):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_204_NO_CONTENT)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class LostPersonImageView(generics.ListCreateAPIView):
    queryset = LostPersonImage.objects.all()
    serializer_class = LostPersonImageSerializer