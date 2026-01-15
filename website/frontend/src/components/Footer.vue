<template>
  <footer class="border-t-2 border-black bg-white py-12">
    <div class="container mx-auto px-4">
      <div class="grid grid-cols-1 gap-8 md:grid-cols-3">
        <div>
          <div class="mb-4 flex items-center gap-2">
            <Flower2 :size="24" class="text-black" />
            <h3 class="text-lg">FlowerShop</h3>
          </div>
          <p class="text-sm text-gray-600">
            Свежие цветы с доставкой. Создаем уникальные букеты для каждого повода.
          </p>
        </div>
        
        <div>
          <h3 class="mb-4 text-lg">Информация</h3>
          <ul class="space-y-2 text-sm text-gray-600">
            <li><a href="#" class="hover:text-black transition-colors">О нас</a></li>
            <li><a href="#" class="hover:text-black transition-colors">Доставка</a></li>
            <li><a href="#" class="hover:text-black transition-colors">Оплата</a></li>
            <li><a href="#" class="hover:text-black transition-colors">Контакты</a></li>
          </ul>
        </div>
        
        <div>
          <h3 class="mb-4 text-lg">Контакты</h3>
          <ul class="space-y-3 text-sm text-gray-600">
            <li class="flex items-center gap-2">
              <Phone :size="16" />
              <span>+7 (495) 123-45-67</span>
            </li>
            <li class="flex items-center gap-2">
              <Mail :size="16" />
              <span>info@flowershop.ru</span>
            </li>
            <li class="flex items-center gap-2">
              <MapPin :size="16" />
              <span>г. Москва, ул. Цветочная, д. 10</span>
            </li>
          </ul>
        </div>
      </div>
      
      <div class="mt-8 border-t border-gray-300 pt-8 text-center text-sm text-gray-600">
        <p>© 2026 FlowerShop. Все права защищены.</p>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { Flower2, Mail, Phone, MapPin } from 'lucide-vue-next'
</script>
