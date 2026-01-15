<template>
  <header class="sticky top-0 z-50 w-full border-b border-black bg-white">
    <div class="container mx-auto flex h-16 items-center justify-between px-4">
      <router-link to="/" class="flex items-center gap-2 hover:opacity-80 transition-opacity cursor-pointer">
        <Flower2 :size="24" class="text-black" />
        <h1 class="text-xl cursor-pointer">FlowerShop</h1>
      </router-link>
      
      <Button variant="outline" size="icon" class="relative border-black" @click="isCartOpen = true">
        <ShoppingCart :size="20" />
        <span
          v-if="totalItems > 0"
          class="absolute -right-2 -top-2 flex h-5 w-5 items-center justify-center rounded-full bg-black text-xs text-white"
        >
          {{ totalItems }}
        </span>
      </Button>
      
      <Sheet :model-value="isCartOpen" @update:model-value="isCartOpen = $event">
        <template #title>Корзина</template>
        <div class="mt-8 flex flex-col gap-4">
          <p v-if="cart.length === 0" class="text-center text-gray-500">
            Ваша корзина пуста
          </p>
          <template v-else>
            <div class="flex flex-col gap-4">
              <div
                v-for="item in cart"
                :key="item.id"
                class="flex gap-4 pb-4 border-b border-gray-200"
              >
                <img
                  :src="item.image"
                  :alt="item.name"
                  class="h-20 w-20 rounded-md object-cover border-2 border-black"
                />
                <div class="flex flex-1 flex-col justify-between">
                  <div>
                    <h3 class="font-medium">{{ item.name }}</h3>
                    <p class="text-sm text-gray-500">
                      ${{ item.price.toFixed(2) }}
                    </p>
                  </div>
                  <div class="flex items-center gap-2">
                    <Button
                      variant="outline"
                      size="sm"
                      class="border-black"
                      @click="handleUpdateQuantity(item.id, Math.max(0, item.quantity - 1))"
                    >
                      -
                    </Button>
                    <span class="w-8 text-center">{{ item.quantity }}</span>
                    <Button
                      variant="outline"
                      size="sm"
                      class="border-black"
                      @click="handleUpdateQuantity(item.id, item.quantity + 1)"
                    >
                      +
                    </Button>
                    <Button
                      variant="ghost"
                      size="sm"
                      @click="handleRemoveFromCart(item.id)"
                      class="ml-auto text-red-500"
                    >
                      Удалить
                    </Button>
                  </div>
                </div>
              </div>
            </div>
            <div class="mt-4 space-y-4">
              <div class="flex justify-between text-lg">
                <span>Итого:</span>
                <span class="font-semibold">${{ totalPrice.toFixed(2) }}</span>
              </div>
              <Button 
                class="w-full bg-black hover:bg-gray-800"
                @click="handleCheckout"
              >
                Оформить заказ
              </Button>
            </div>
          </template>
        </div>
      </Sheet>
    </div>
  </header>
</template>

<script setup>
import { ref, computed, inject } from 'vue'
import { useRouter } from 'vue-router'
import { ShoppingCart, Flower2 } from 'lucide-vue-next'
import Button from './ui/Button.vue'
import Sheet from './ui/Sheet.vue'

const router = useRouter()

const props = defineProps({
  cart: {
    type: Array,
    default: null
  }
})

const emit = defineEmits(['remove-from-cart', 'update-quantity'])

// Используем inject для получения корзины, если не передана через props
const injectedCart = inject('cart')
const cart = computed(() => props.cart || injectedCart.value)

const isCartOpen = ref(false)

const totalItems = computed(() => {
  return cart.value.reduce((sum, item) => sum + item.quantity, 0)
})

const totalPrice = computed(() => {
  return cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
})

const handleUpdateQuantity = (id, quantity) => {
  if (quantity === 0) {
    handleRemoveFromCart(id)
  } else {
    if (props.cart) {
      emit('update-quantity', id, quantity)
    } else {
      const item = injectedCart.value.find((item) => item.id === id)
      if (item) {
        item.quantity = quantity
      }
    }
  }
}

const handleRemoveFromCart = (id) => {
  if (props.cart) {
    emit('remove-from-cart', id)
  } else {
    injectedCart.value = injectedCart.value.filter((item) => item.id !== id)
  }
}

const handleCheckout = () => {
  if (cart.value.length === 0) {
    return
  }
  isCartOpen.value = false
  router.push('/checkout')
}
</script>
